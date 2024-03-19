package project.study.chat.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.study.constant.WebConst;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.RestFulException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ChatAccessToken {

    private final ChatCurrentMemberManager chatCurrentMemberManager;
    private final Map<String, Long> encodeToken; // 암호화된 Token, memberId

    @Autowired
    public ChatAccessToken(ChatCurrentMemberManager chatCurrentMemberManager) {
        this.encodeToken = new HashMap<>();
        this.chatCurrentMemberManager = chatCurrentMemberManager;
    }

    public String createAccessToken(Member member, Long roomId) {
        Long memberId = member.getMemberId();
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        chatCurrentMemberManager.plus(roomId, member);
        encodeToken.put(token, memberId);
        return token;
    }

    public Long getMemberId(String token, Long roomId) {
        Long memberId = encodeToken.getOrDefault(token, 0L);
        Long room = chatCurrentMemberManager.getRoomId(memberId, roomId);
        if (room == null || !room.equals(roomId)) throw new RestFulException(new ResponseDto(WebConst.ERROR, "권한 없음"));
        return memberId;
    }

    public void remove(Long roomId, Long memberId) {
        chatCurrentMemberManager.minus(roomId, memberId);

        for (String token : encodeToken.keySet()) {
            if (encodeToken.get(token).equals(memberId)) {
                encodeToken.remove(token);
                return;
            }
        }
    }
}
