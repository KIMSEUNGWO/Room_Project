package project.study.dto.login.validator;

import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;

public class SocialMemberValidator implements MemberValidator {
    @Override
    public void validLogin(Member member) {

    }

    @Override
    public void validSignup(RequestSignupDto signupDto) {

    }
}
