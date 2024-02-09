package project.study.dto.login.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultSignupError {

    private String location;
    private String message;
}
