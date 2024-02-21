package project.study.authority.member.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.*;
import project.study.domain.Member;
import project.study.domain.Room;
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
    public void editNotice(Room room, RequestNoticeDto data) {
        System.out.println("editNotice 실행");
    }

    @Override
    public void kickMember(Room room, RequestKickDto data) {
        System.out.println("kickMember 실행");
    }

    @Override
    public void deleteRoom(Room room, RequestDeleteRoomDto data) {
        System.out.println("deleteRoom 실행");
    }
}
