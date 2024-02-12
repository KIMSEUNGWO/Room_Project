package project.study.exceptions.kakaologin;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
public class SocialException extends RuntimeException {

    private final HttpServletResponse response;
    private final String alertMessage;

    public SocialException(HttpServletResponse response, String alertMessage) {
        this.response = response;
        this.alertMessage = alertMessage;
    }
}
