package project.study.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class ChatRoom {

    private Long roomId;
    private long userCount;

    private List<String> userList = new ArrayList<>();

    public ChatRoom(Long roomId) {
        this.roomId = roomId;
    }

}
