package project.study.authority.member.authority;

import project.study.authority.member.dto.*;
import project.study.domain.Member;
import project.study.domain.Room;

public interface ManagerAuthority {

    void editRoom(Room room, RequestEditRoomDto data);
    void managerEntrust(Room room, RequestEntrustDto data);
    void editNotice(Room room, RequestNoticeDto data);
    void kickMember(Room room, RequestKickDto data);
    void deleteRoom(Room room, RequestDeleteRoomDto data);
}
