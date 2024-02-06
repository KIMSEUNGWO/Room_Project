package project.study.dto.login;

import jakarta.servlet.http.HttpSession;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;

public class KakaoMember implements MemberInterface{
    @Override
    public Member signup(RequestSignupDto signupDto) {
        return null;
    }

    @Override
    public Member login(RequestLoginDto loginDto, HttpSession session) {
        return null;
    }
}
