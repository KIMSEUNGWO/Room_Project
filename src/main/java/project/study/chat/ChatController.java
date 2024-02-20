package project.study.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import project.study.chat.dto.ChatDto;
import project.study.chat.dto.ChatMemberListDto;
import project.study.domain.Member;
import project.study.domain.Room;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

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

        ChatMemberListDto chatDto = chatService.changeDto(chat);
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chatDto);
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
                .message(member.getMemberNickname() + "님이 나가셨습니다.")
                .build();
        ChatMemberListDto chatMemberListDto = chatService.changeDto(chat);

        template.convertAndSend("/sub/chat/room/" + room.getRoomId(), chatMemberListDto);

    }
}
