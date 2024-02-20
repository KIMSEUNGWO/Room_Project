package project.study.chat.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ChatCurrentMemberManager {

    private final Map<Long, Set<String>> currentChatMember;

    public void plus(Long roomId, String nickname) {
        if (!currentChatMember.containsKey(roomId)) {
            currentChatMember.put(roomId, new HashSet<>());
        }
        Set<String> nicknameList = currentChatMember.get(roomId);
        nicknameList.add(nickname);
    }
    public void minus(Long roomId, String nickname) {
        Set<String> nicknameList = currentChatMember.get(roomId);
        nicknameList.remove(nickname);
    }

    public List<String> getMemberList(Long roomId) {
        System.out.println("currentChatMember = " + currentChatMember.get(roomId));
        return currentChatMember.get(roomId).stream().toList();
    }
}
