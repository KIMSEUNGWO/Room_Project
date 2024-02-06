package project.study.dto.login.validator;

import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;

public interface MemberValidator {

    void validLogin(Member member);

    void validSignup(RequestSignupDto signupDto);
}
