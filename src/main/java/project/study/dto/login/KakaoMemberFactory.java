package project.study.dto.login;

public class KakaoMemberFactory implements MemberFactory{
    @Override
    public MemberInterface createMember() {
        return new KakaoMember();
    }
}
