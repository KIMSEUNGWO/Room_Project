package project.study.authority.member.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.*;
import project.study.domain.Room;
import project.study.domain.RoomNotice;
import project.study.dto.room.ResponseRoomNotice;
import project.study.service.RoomService;

@Component
@Transactional
@RequiredArgsConstructor
public class ManagerAuthorityImpl implements ManagerAuthority{

    private final RoomService roomService;

    @Override
    public void editRoom(Room room, RequestEditRoomDto data) {
        System.out.println("editRoom 실행");

        roomService.validEditRoomData(room, data);
        roomService.editRoom(room, data);

    }

    @Override
    public void managerEntrust(Room room, RequestEntrustDto data) {
        System.out.println("managerEntrust 실행");
    }

    @Override
    public ResponseRoomNotice uploadNotice(Room room, RequestNoticeDto data) {
        RoomNotice roomNotice = room.getRoomNotice();
        if (room.hasNotice()) {
            roomNotice.updateNotice(data.getNotice());
        } else {
            roomNotice = roomService.saveRoomNotice(room, data);
        }

        return ResponseRoomNotice
            .builder()
            .content(roomNotice.getRoomNoticeContent())
            .time(roomNotice.getRoomNoticeDate())
            .build();
    }

    @Override
    public void deleteNotice(Room room) {
        if (room.hasNotice()) {
            roomService.deleteRoomNotice(room);
        }
    }

    @Override
    public void kickMember(Room room, RequestKickDto data) {
        System.out.println("kickMember 실행");
    }
    
}
