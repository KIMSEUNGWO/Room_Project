package project.study.authority.member.authority;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.domain.Member;

@Component
public class MemberAuthorityImpl implements MemberAuthority{
    @Override
    public void createRoom(Member member, RequestCreateRoomDto data) {
        System.out.println("createRoom 실행");
    }

    @Override
    public void notify(Member member, RequestNotifyDto data) {
        System.out.println("notify 실행");
    }
}
