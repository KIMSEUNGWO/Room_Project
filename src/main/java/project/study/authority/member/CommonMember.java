package project.study.authority.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;

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
    public List<ResponseRoomListDto> getMyRoomList(Member member) {
        return memberAuthority.getMyRoomList(member);
    }

    @Override
    public JoinRoom joinRoom(Member member, Room room) {
        return memberAuthority.joinRoom(member, room);
    }
}
