package project.study.chat;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.authority.ManagerAuthority;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.RequestEntrustDto;
import project.study.authority.member.dto.RequestKickDto;
import project.study.authority.member.dto.RequestNoticeDto;
import project.study.chat.component.ChatAccessToken;
import project.study.chat.dto.*;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.domain.RoomNotice;
import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.RestFulException;
import project.study.service.JoinRoomService;
import project.study.service.RoomService;

import java.time.LocalDateTime;
import java.util.Optional;

import static project.study.constant.WebConst.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    private final MemberAuthorizationCheck authorizationCheck;
    private final RoomService roomService;
    private final ChatAccessToken chatAccessToken;
    private final JoinRoomService joinRoomService;


    @GetMapping("/room/{room}/access")
    public ResponseEntity<ResponseDto> accessToken(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, @PathRoom("room") Room room) {
        boolean exitsByMemberAndRoom = joinRoomService.exitsByMemberAndRoom(memberId, room);
        if (!exitsByMemberAndRoom) throw new RestFulException(new ResponseDto(ERROR, "권한 없음"));

        chatAccessToken.createAccessToken(memberId, room.getRoomId());
        return ResponseEntity.ok(new ResponseDto(String.valueOf(memberId)));
    }

    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("enterUser = " + chat);
        Member member  = chatService.getMember(chat.getToken(), chat.getRoomId());
        chatService.accessCount(chat, member); // 현재 접속중인 회원 리스트에 추가

        boolean hasPhone = member.hasPhone();

        headerAccessor.getSessionAttributes().put("member", member);
        headerAccessor.getSessionAttributes().put("hasPhone", hasPhone);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setSenderImage(member.getStoreImage());
        chat.setSender(member.getMemberNickname());
        chat.setMessage(member.getMemberNickname() + "님이 입장하셨습니다.");

        ChatObject<ResponseChatMemberList> responseChatMemberListChatObject = chatService.changeToMemberListDto(chat);
        templateSendMessage(chat.getRoomId(), responseChatMemberListChatObject);

        if (!hasPhone) {
            ChatDto requirePhone = ChatDto.builder().type(MessageType.REQUIRE_PHONE).build();
            templateSendMessage(chat.getRoomId(), requirePhone);
        }
    }

    @MessageMapping("/chat/update")
    public void roomUpdate(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        Optional<Room> findRoom = roomService.findById(roomId);
        if (findRoom.isEmpty()) return;
        Room room = findRoom.get();

        Room.ResponseRoomUpdateInfo roomInfo = room.getResponseRoomUpdateInfo();

        chat.setMessage("방 설정이 변경되었습니다.");
        chat.setTime(LocalDateTime.now());

        templateSendMessage(roomId, new ChatObject<>(chat, roomInfo));

    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        boolean hasPhone = (boolean) headerAccessor.getSessionAttributes().get("hasPhone");
        if (!hasPhone) {
            return;
        }

        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        Optional<Room> findRoom = roomService.findById(roomId);
        if (findRoom.isEmpty()) return;
        Room room = findRoom.get();

        chat.setSenderImage(member.getStoreImage());
        chat.setSender(member.getMemberNickname());
        chat.setMessage(chat.getMessage());
        chat.setTime(LocalDateTime.now());
        System.out.println("chat = " + chat);

        chatService.saveChat(chat, member, room);

        templateSendMessage(roomId, chat);

    }

    @ResponseBody
    @PostMapping(value = "/room/exit")
    public ResponseEntity<ResponseDto> exitRoom(@SessionLogin(required = true) Member member, @RequestBody String roomId, HttpServletResponse response) {
        Room room = roomService.findByRoom(roomId, response);

        MemberAuthority commonMember = authorizationCheck.getMemberAuthority(response, member);
        commonMember.exitRoom(member, room, response);

        chatService.accessRemove(member, room.getRoomId());

        templateSendMessage(room.getRoomId(), chatService.exitRoom(member, room));
        return ResponseEntity.ok(new ResponseDto("방 탈퇴 완료"));
    }


    // 회원 강퇴
    @ResponseBody
    @DeleteMapping("/room/{room}/kick")
    public ResponseEntity<ResponseDto> roomKick(@SessionLogin(required = true) Member member,
                                                @PathRoom("room") Room room,
                                                HttpServletResponse response,
                                                @RequestBody RequestKickDto data) {
        System.out.println("강퇴당하는 회원의 닉네임 = " + data.getNickname());
        ManagerAuthority managerMember = authorizationCheck.getManagerAuthority(response, member, room);
        Member kickMember = managerMember.kickMember(response, room, data);

        chatService.accessRemove(kickMember, room.getRoomId());

        ChatDto kick = chatService.kickRoom(kickMember, room);
        templateSendMessage(room.getRoomId(), kick);

        return ResponseEntity.ok(new ResponseDto("성공"));
    }
    // 매니저 위임
    @ResponseBody
    @PostMapping("/room/{room}/entrust")
    public ResponseEntity<ResponseDto> roomManagerEntrust(@SessionLogin(required = true) Member member, @PathRoom("room") Room room,
                                            HttpServletResponse response,
                                            @RequestBody RequestEntrustDto data) {
        System.out.println("방장 위임 회원의 닉네임 : " + data.getNickname());
        ManagerAuthority managerMember = authorizationCheck.getManagerAuthority(response, member, room);
        Member nextManager = managerMember.managerEntrust(member, room, data);

        ChatDto chat = chatService.nextManagerRoom(nextManager, room);

        templateSendMessage(room.getRoomId(), chat);
        return ResponseEntity.ok(new ResponseDto("성공"));
    }
    // 공지사항 등록 및 수정
    @ResponseBody
    @PostMapping("/room/{room}/notice")
    public ResponseEntity<ResponseDto> roomUploadNotice(@SessionLogin(required = true) Member member, @PathRoom("room") Room room,
                                            HttpServletResponse response,
                                            @RequestBody RequestNoticeDto data) {
        System.out.println("data = " + data);
        ManagerAuthority managerMember = authorizationCheck.getManagerAuthority(response, member, room);

        RoomNotice.ResponseRoomNotice roomNotice = managerMember.uploadNotice(room, data);

        ChatDto chat = ChatDto.builder()
            .roomId(room.getRoomId())
            .type(MessageType.NOTICE)
            .sender(member.getMemberNickname())
            .time(LocalDateTime.now())
            .token(member.getMemberId())
            .message("공지사항이 등록되었습니다.")
            .build();

        templateSendMessage(room.getRoomId(), new ChatObject<>(chat, roomNotice));
        return ResponseEntity.ok(new ResponseDto("성공"));
    }

    // 공지사항 삭제
    @ResponseBody
    @DeleteMapping("/room/{room}/notice/delete")
    public ResponseEntity<ResponseDto> roomDeleteNotice(@SessionLogin(required = true) Member member, @PathRoom("room") Room room,
                                                        HttpServletResponse response) {

        ManagerAuthority managerMember = authorizationCheck.getManagerAuthority(response, member, room);

        if (!room.hasNotice()) {
            return ResponseEntity.ok(new ResponseDto(ERROR, "공지사항이 존재하지 않습니다."));
        }

        managerMember.deleteNotice(room);

        ChatDto chat = ChatDto.builder()
            .roomId(room.getRoomId())
            .type(MessageType.NOTICE)
            .sender(member.getMemberNickname())
            .time(LocalDateTime.now())
            .token(member.getMemberId())
            .message("공지사항이 삭제되었습니다.")
            .build();

        templateSendMessage(room.getRoomId(), chat);

        return ResponseEntity.ok(new ResponseDto("성공"));
    }


    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("Disconnect " + event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");
        Optional<Room> findRoom = roomService.findById(roomId);
        if (findRoom.isEmpty()) return;
        Room room = findRoom.get();

        chatService.accessRemove(member, room.getRoomId());

        log.info("headAccessor {}", headerAccessor);

        ChatDto chat = ChatDto.builder()
                .type(MessageType.LEAVE)
                .roomId(room.getRoomId())
                .sender(member.getMemberNickname())
                .message(member.getMemberNickname() + "님이 채팅방에서 나가셨습니다.")
                .build();

        templateSendMessage(room.getRoomId(), chatService.changeToMemberListDto(chat));
    }

    private void templateSendMessage(Long roomId, ChatDto chat) {
        template.convertAndSend("/sub/chat/room/" + roomId, chat);
    }
}
