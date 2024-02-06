package project.study.dto.login;


import project.study.domain.Member;

public interface MemberFactory {

    MemberInterface createMember();

    default MemberInterface signup(RequestSignupDto signupDto) {
        MemberInterface member = createMember();
        member.signup(signupDto);
        return member;
    }

    default Member login(RequestLoginDto loginDto) {
        MemberInterface memberInterface = createMember();
        return memberInterface.login(loginDto);
    }

}
