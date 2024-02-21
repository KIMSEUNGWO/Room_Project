package project.study.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomUpdateDto extends ChatDto {

    private ResponseRoomUpdateInfo roomInfo;

    public ChatRoomUpdateDto(ChatDto chat, ResponseRoomUpdateInfo roomInfo) {
        super(chat.getType(), chat.getRoomId(), chat.getToken(), chat.getSender(), chat.getSenderImage(), chat.getMessage(), chat.getTime());
        this.roomInfo = roomInfo;
    }

}
