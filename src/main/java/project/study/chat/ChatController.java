package project.study.chat;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import project.study.authority.member.CommonMember;
import project.study.authority.member.ManagerMember;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.dto.RequestKickDto;
import project.study.chat.dto.*;
import project.study.constant.WebConst;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.dto.abstractentity.ResponseDto;
import project.study.service.RoomService;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    private final MemberAuthorizationCheck authorizationCheck;
    private final RoomService roomService;

    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("enterUser = " + chat);
        Member member  = chatService.getMember(chat.getToken(), chat.getRoomId());
        chatService.accessCount(chat, member); // 현재 접속중인 회원 리스트에 추가
        Room room = chatService.findByRoom(chat.getRoomId());

        headerAccessor.getSessionAttributes().put("member", member);
        headerAccessor.getSessionAttributes().put("room", room);

        if (member.getProfile() != null) {
            headerAccessor.getSessionAttributes().put("senderImage", member.getProfile().getProfileStoreName());
            chat.setSenderImage(member.getProfile().getProfileStoreName());
        }

        chat.setSender(member.getMemberNickname());
        chat.setMessage(member.getMemberNickname() + "님이 입장하셨습니다.");

        ChatObject<ResponseChatMemberList> responseChatMemberListChatObject = chatService.changeToMemberListDto(chat);
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), responseChatMemberListChatObject);
    }

    @MessageMapping("/chat/update")
    public void roomUpdate(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        Room room = (Room) headerAccessor.getSessionAttributes().get("room");

        ResponseRoomUpdateInfo roomInfo = chatService.getResponseRoomUpdateInfo(room);

        chat.setMessage("방 설정이 변경되었습니다.");
        chat.setTime(LocalDateTime.now());

        ChatObject<ResponseRoomUpdateInfo> chatObject = new ChatObject<>(chat, roomInfo);

        template.convertAndSend("/sub/chat/room/" + room.getRoomId(), chatObject);

    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {

        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        Room room = (Room) headerAccessor.getSessionAttributes().get("room");
        String senderImage = (String) headerAccessor.getSessionAttributes().get("senderImage");

        chat.setSenderImage(senderImage);
        chat.setSender(member.getMemberNickname());
        chat.setMessage(chat.getMessage());
        chat.setTime(LocalDateTime.now());
        System.out.println("chat = " + chat);

        chatService.saveChat(chat, member, room);

        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);

    }

    @ResponseBody
    @PostMapping(value = "/room/exit")
    public ResponseEntity<ResponseDto> exitRoom(@SessionLogin(required = true) Member member, @RequestBody String roomId, HttpServletResponse response) {
        Room room = roomService.findByRoom(roomId, response);

        CommonMember commonMember = authorizationCheck.getCommonMember(response, member);
        commonMember.exitRoom(member, room, response);

        ChatObject<ResponseNextManager> chat = chatService.exitRoom(member, room);
        template.convertAndSend("/sub/chat/room/" + roomId, chat);

        return new ResponseEntity<>(new ResponseDto(WebConst.OK, "방 탈퇴 완료"), HttpStatus.OK);
    }


//    @ResponseBody
//    @PostMapping("/room/{room}")
//    public ResponseEntity<ResponseDto> room(@SessionLogin(required = true) Member member, @PathRoom("room") Room room,
//                                            HttpServletResponse response,
//                                            @ModelAttribute RequestKickDto data) {
//        ManagerMember managerMember = authorizationCheck.getManagerMember(response, member, room);
//        managerMember.kickMember(room, data);
//
//        return null;
//    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("Disconnect " + event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        Member member = (Member) headerAccessor.getSessionAttributes().get("member");
        Room room = (Room) headerAccessor.getSessionAttributes().get("room");

        chatService.accessRemove(member.getMemberId(), room.getRoomId(), member.getMemberNickname());

        log.info("headAccessor {}", headerAccessor);


        ChatDto chat = ChatDto.builder()
                .type(MessageType.LEAVE)
                .roomId(room.getRoomId())
                .sender(member.getMemberNickname())
                .message(member.getMemberNickname() + "님이 채팅방에서 나가셨습니다.")
                .build();
        ChatObject<ResponseChatMemberList> responseChatMemberListChatObject = chatService.changeToMemberListDto(chat);

        template.convertAndSend("/sub/chat/room/" + room.getRoomId(), responseChatMemberListChatObject);

    }
}
