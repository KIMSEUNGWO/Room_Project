package project.study.dto.login;


import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;
import project.study.constant.WebConst;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;
import project.study.dto.login.validator.MemberValidator;

import static project.study.constant.WebConst.LOGIN_MEMBER;

public interface MemberFactory {

    MemberInterface createMember();
    MemberValidator validator();

    @Transactional
    default Member signup(RequestSignupDto signupDto) {
        MemberInterface memberInterface = createMember();

        MemberValidator validator = validator();
        validator.validSignup(signupDto);

        return memberInterface.signup(signupDto);
    }

    @Transactional
    default Member login(RequestLoginDto loginDto, HttpSession session) {
        MemberInterface memberInterface = createMember();
        Member member = memberInterface.login(loginDto);

        MemberValidator validator = validator();
        validator.validLogin(member);

        session.setAttribute(LOGIN_MEMBER, member.getMemberId());

        return member;
    }

}
