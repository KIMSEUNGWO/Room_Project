package project.study.dto.login;

import project.study.enums.SocialEnum;

public class RequestSocialLoginDto implements RequestLoginDto {

    private SocialEnum socialEnum;
    private String email;
}
