package project.study.chat.dto;

import lombok.Getter;

@Getter
public class ChatNextManagerDto extends ChatDto {

    private String nextManager;
    private Long token;

    public ChatNextManagerDto(ChatDto chat, String nextManager, Long token) {
        super(chat.getType(), chat.getRoomId(), chat.getToken(), chat.getSender(), chat.getSenderImage(), chat.getMessage(), chat.getTime());
        this.nextManager = nextManager;
        this.token = token;
    }

}
