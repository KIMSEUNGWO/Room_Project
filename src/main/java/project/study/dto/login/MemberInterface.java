package project.study.dto.login;

import jakarta.servlet.http.HttpSession;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;

public interface MemberInterface {


    Member signup(RequestSignupDto signupDto);


    Member login(RequestLoginDto loginDto, HttpSession session);
}
