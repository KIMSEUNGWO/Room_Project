package project.study.exceptions.authority;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException {

    private final HttpServletResponse response;
    private final String alertMessage;

    public AuthorizationException(HttpServletResponse response, String alertMessage) {
        this.response = response;
        this.alertMessage = alertMessage;
    }
}
