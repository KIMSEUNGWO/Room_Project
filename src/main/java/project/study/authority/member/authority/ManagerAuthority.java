package project.study.authority.member.authority;

import project.study.authority.member.dto.RequestEditRoomDto;
import project.study.authority.member.dto.RequestEntrustDto;
import project.study.authority.member.dto.RequestKickDto;
import project.study.authority.member.dto.RequestNoticeDto;
import project.study.domain.Member;

public interface ManagerAuthority {

    void editRoom(Member member, RequestEditRoomDto data);
    void managerEntrust(Member member, RequestEntrustDto data);
    void editNotice(Member member, RequestNoticeDto data);
    void kickMember(Member member, RequestKickDto data);
}
