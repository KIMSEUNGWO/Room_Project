package project.study.dto.login.validator;

import jakarta.servlet.http.HttpServletResponse;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;

public interface MemberValidator {

    void validLogin(Member member, HttpServletResponse response);

    void validSignup(RequestSignupDto signupDto);
}
