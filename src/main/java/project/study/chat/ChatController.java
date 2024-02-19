package project.study.chat;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import project.study.constant.WebConst;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.jpaRepository.MemberJpaRepository;

import java.util.Map;
import java.util.Optional;

import static project.study.constant.WebConst.LOGIN_MEMBER;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations template;
    private final ChatRepository chatRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final ChatAccessToken chatAccessToken;

    @MessageMapping("/chat/enterUser")
    @SendTo("/sub/chat/room")
    public ChatDto enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor) {

        Long memberId = chatAccessToken.getMemberId(chat.getSender());

        Optional<Member> findMember = memberJpaRepository.findById(memberId);
        Member member = findMember.get();
        chat.setSender(member.getMemberNickname());


        System.out.println("enterUser = " + chat);

        headerAccessor.getSessionAttributes().put("userNickname", member.getMemberNickname());
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(member.getMemberNickname() + "님이 입장하셨습니다.");
//        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
        return chat;

    }

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        System.out.println("chat = " + chat);
        chat.setMessage(chat.getMessage());
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
        // 채팅방 유저 -1
        chatRepository.minusUserCnt(roomId);

        chatRepository.delUser(Long.parseLong(roomId), userNickname);

        ChatDto chat = ChatDto.builder()
                .type(MessageType.LEAVE)
                .sender(userNickname)
                .message(userNickname + "님이 나가셨습니다.")
                .build();
        template.convertAndSend("/sub/chat/room/" + roomId, chat);

    }
}
