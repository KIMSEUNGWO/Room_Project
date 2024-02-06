package project.study.dto.login;

public class DefaultMemberFactory implements MemberFactory{
    @Override
    public MemberInterface createMember() {
        return new DefaultMember();
    }
}
