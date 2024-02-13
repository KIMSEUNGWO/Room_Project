package project.study.authority.member.authority;

import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.domain.Member;

public interface MemberAuthority {

    void createRoom(Member member, RequestCreateRoomDto data);
    void notify(Member member, RequestNotifyDto data);
}
