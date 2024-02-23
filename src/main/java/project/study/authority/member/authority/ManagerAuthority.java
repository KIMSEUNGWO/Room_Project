package project.study.authority.member.authority;

import project.study.authority.member.dto.*;
import project.study.domain.Room;
import project.study.dto.room.ResponseRoomNotice;

public interface ManagerAuthority {

    void editRoom(Room room, RequestEditRoomDto data);
    void managerEntrust(Room room, RequestEntrustDto data);
    ResponseRoomNotice uploadNotice(Room room, RequestNoticeDto data);
    void deleteNotice(Room room);
    void kickMember(Room room, RequestKickDto data);
}
