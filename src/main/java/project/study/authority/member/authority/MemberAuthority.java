package project.study.authority.member.authority;

import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.domain.Member;

import java.util.List;

public interface MemberAuthority {

    Long createRoom(Member member, RequestCreateRoomDto data);
    void notify(Member member, RequestNotifyDto data);

    List<ResponseRoomListDto> getMyRoomList(Member member);
}
