package project.study.chat;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ChatRepository {

    private Map<Long, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    public ChatRoom createChatRoom(Long roomId) {
        ChatRoom chatRoom = new ChatRoom(roomId);
        chatRoomMap.put(roomId, chatRoom);
        return chatRoom;
    }

    // 채팅방 인원+1
    public void plusUserCnt(Long roomId){
        ChatRoom room = chatRoomMap.get(roomId);
        if (room == null) {
            room = createChatRoom(roomId);
        }
        room.setUserCount(room.getUserCount()+1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId){
        ChatRoom room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    public String addUser(Long roomId, String userNickname){
        ChatRoom room = chatRoomMap.get(roomId);

        room.getUserList().add(userNickname);

        return userNickname;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(Long roomId, String userNickname){
        ChatRoom room = chatRoomMap.get(roomId);
        room.getUserList().remove(userNickname);
    }

    // 채팅방 전체 userlist 조회
    public List<String> getUserList(Long roomId){
        List<String> list = new ArrayList<>();

        ChatRoom room = chatRoomMap.get(roomId);

        return room.getUserList();
    }
}
