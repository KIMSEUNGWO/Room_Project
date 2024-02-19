package project.study.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatAccessToken {

    private final Map<String, Long> accessToken;

    @Autowired
    public ChatAccessToken() {
        this.accessToken = new ConcurrentHashMap<>();
    }

    public String createAccessToken(Long memberId) {
        if (accessToken.containsValue(memberId)) {
            return null;
        }
        String token = createUUID();
        accessToken.put(token, memberId);
        return token;
    }

    public Long getMemberId(String token) {
        return accessToken.get(token);
    }

    private String createUUID() {
        String temp = UUID.randomUUID().toString();
        while (accessToken.containsKey(temp)) {
            temp = UUID.randomUUID().toString();
        }
        return temp;
    }

    public void remove(String token) {
        accessToken.remove(token);
    }
}
