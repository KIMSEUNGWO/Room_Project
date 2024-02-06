package project.study.dto.login.requestdto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RequestDefaultLoginDto implements RequestLoginDto {

    private String account;
    private String password;
}
