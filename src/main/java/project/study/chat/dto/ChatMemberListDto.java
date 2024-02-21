package project.study.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatMemberListDto extends ChatDto {

    private List<String> currentMemberList;

    public ChatMemberListDto(ChatDto chat, List<String> currentMemberList) {
        super(chat.getType(), chat.getRoomId(), chat.getToken(), chat.getSender(), chat.getSenderImage(), chat.getMessage(), chat.getTime());
        this.currentMemberList = currentMemberList;
    }

}
