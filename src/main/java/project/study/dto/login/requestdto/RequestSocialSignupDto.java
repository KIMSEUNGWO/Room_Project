package project.study.dto.login.requestdto;

import lombok.Getter;
import project.study.enums.SocialEnum;

@Getter
public class RequestSocialSignupDto implements RequestSignupDto{

    private final SocialEnum socialEnum;
    private final String name;
    private final String email;
    private final String nickName;
    private final String phone;
    public RequestSocialSignupDto(RequestSocialLoginDto data) {
        this.socialEnum = data.getSocialEnum();
        this.name = data.getName();
        this.email = data.getEmail();
        this.nickName = data.getEmail();
        this.phone = data.getPhone();
    }
}
