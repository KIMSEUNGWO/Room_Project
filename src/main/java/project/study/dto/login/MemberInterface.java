package project.study.dto.login;

import project.study.domain.Member;

public interface MemberInterface {


    Member signup(RequestSignupDto signupDto);


    Member login(RequestLoginDto loginDto);
}
