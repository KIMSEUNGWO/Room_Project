package project.study.dto.login;

import lombok.Getter;

@Getter
public class RequestDefaultSignupDto implements RequestSignupDto{

    private String account;
    private String password;
    private String passwordCheck;
    private String name;
    private String nickName;
}
