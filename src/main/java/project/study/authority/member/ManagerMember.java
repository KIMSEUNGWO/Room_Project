package project.study.authority.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.authority.ManagerAuthority;
import project.study.authority.member.dto.*;
import project.study.domain.Room;
import project.study.dto.room.ResponseRoomNotice;

@Component
@RequiredArgsConstructor
public class ManagerMember implements ManagerAuthority {

    private final ManagerAuthority managerAuthority;

    @Override
    public void editRoom(Room room, RequestEditRoomDto data) {
        managerAuthority.editRoom(room, data);
    }
    @Override
    public void managerEntrust(Room room, RequestEntrustDto data) {
        managerAuthority.managerEntrust(room, data);
    }
    @Override
    public ResponseRoomNotice uploadNotice(Room room, RequestNoticeDto data) {
        return managerAuthority.uploadNotice(room, data);
    }

    @Override
    public void deleteNotice(Room room) {
        managerAuthority.deleteNotice(room);
    }

    @Override
    public void kickMember(Room room, RequestKickDto data) {
        managerAuthority.kickMember(room, data);
    }

}
