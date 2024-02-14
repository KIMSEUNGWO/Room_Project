package project.study.authority.member;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.authority.member.dto.ResponseMyRoomListDto;
import project.study.domain.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonMember implements MemberAuthority{

    private final MemberAuthority memberAuthority;

    @Override
    public Long createRoom(Member member, RequestCreateRoomDto data) {
        return memberAuthority.createRoom(member, data);
    }
    @Override
    public void notify(Member member, RequestNotifyDto data) {
        memberAuthority.notify(member, data);
    }

    @Override
    public List<ResponseMyRoomListDto> getMyRoomList(Member member) {
        return memberAuthority.getMyRoomList(member);
    }
}
