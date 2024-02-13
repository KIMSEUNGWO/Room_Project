package project.study.authority.member;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.domain.Member;

@Component
@RequiredArgsConstructor
public class CommonMember implements MemberAuthority{

    private final MemberAuthority memberAuthority;

    @Override
    public void createRoom(Member member, RequestCreateRoomDto data) {
        memberAuthority.createRoom(member, data);
    }
    @Override
    public void notify(Member member, RequestNotifyDto data) {
        memberAuthority.notify(member, data);
    }
}
