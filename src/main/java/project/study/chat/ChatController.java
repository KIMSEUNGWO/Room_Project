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
import project.study.domain.Member;
import project.study.jpaRepository.MemberJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatRepository chatRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ChatAccessToken chatAccessToken;

    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("enterUser = " + chat);
        Long memberId = chatAccessToken.getMemberId(chat.getToken());
        Optional<Member> findMember = memberJpaRepository.findById(memberId);
        Member member = findMember.get();


        headerAccessor.getSessionAttributes().put("memberId", memberId);
        headerAccessor.getSessionAttributes().put("nickname", member.getMemberNickname());
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        if (member.getProfile() != null) {
            headerAccessor.getSessionAttributes().put("senderImage", member.getProfile().getProfileStoreName());
            chat.setSenderImage(member.getProfile().getProfileStoreName());
        }

        chat.setSender(member.getMemberNickname());
        chat.setMessage(member.getMemberNickname() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {

        Long memberId = (Long) headerAccessor.getSessionAttributes().get("memberId");
        String nickname = (String) headerAccessor.getSessionAttributes().get("nickname");
        String senderImage = (String) headerAccessor.getSessionAttributes().get("senderImage");

        chat.setSenderImage(senderImage);
        chat.setSender(nickname);
        chat.setMessage(chat.getMessage());
        chat.setTime(LocalDateTime.now());
        System.out.println("chat = " + chat);
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("Disconnect " + event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userNickname = (String) headerAccessor.getSessionAttributes().get("userNickname");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);


        ChatDto chat = ChatDto.builder()
                .type(MessageType.LEAVE)
                .sender(userNickname)
                .message(userNickname + "님이 나가셨습니다.")
                .build();
        template.convertAndSend("/sub/chat/room/" + roomId, chat);

    }
}
