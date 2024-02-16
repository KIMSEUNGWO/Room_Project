package project.study.authority.member.authority;

import project.study.authority.member.dto.*;
import project.study.domain.Member;

public interface ManagerAuthority {

    void editRoom(Member member, RequestEditRoomDto data);
    void managerEntrust(Member member, RequestEntrustDto data);
    void editNotice(Member member, RequestNoticeDto data);
    void kickMember(Member member, RequestKickDto data);
    void deleteRoom(Member member, RequestDeleteRoomDto data);
}
