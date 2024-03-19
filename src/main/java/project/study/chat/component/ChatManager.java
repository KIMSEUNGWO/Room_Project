package project.study.chat.component;

import project.study.chat.MessageType;
import project.study.chat.dto.ChatDto;
import project.study.domain.Member;
import project.study.domain.Room;

import java.time.LocalDateTime;

public class ChatManager {

    public static ChatDto sendMessageChatDto(Member member, Room room, MessageType messageType, String message) {
        return ChatDto.builder()
                .roomId(room.getRoomId())
                .type(messageType)
                .sender(member.getMemberNickname())
                .time(LocalDateTime.now())
                .message(message)
                .build();
    }
}