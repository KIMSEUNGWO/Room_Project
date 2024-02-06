package project.study.dto.login;

import project.study.dto.login.validator.MemberValidator;
import project.study.dto.login.validator.SocialMemberValidator;

public class KakaoMemberFactory implements MemberFactory{
    @Override
    public MemberInterface createMember() {
        return new KakaoMember();
    }

    @Override
    public MemberValidator validator() {
        return new SocialMemberValidator();
    }
}
