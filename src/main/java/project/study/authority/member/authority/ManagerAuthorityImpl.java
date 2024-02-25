package project.study.authority.member.authority;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.*;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.domain.RoomNotice;
import project.study.dto.room.ResponseRoomNotice;
import project.study.exceptions.authority.AuthorizationException;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.service.RoomService;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class ManagerAuthorityImpl implements ManagerAuthority{

    private final RoomService roomService;
    private final MemberJpaRepository memberJpaRepository;

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
    public Member kickMember(HttpServletResponse response, Room room, RequestKickDto data) {
        System.out.println("kickMember 실행");
        Optional<Member> findMember = memberJpaRepository.findByMemberNickname(data.getKickMemberNickname());
        if (findMember.isEmpty()) throw new AuthorizationException(response, "존재하지 않는 회원입니다.");
        Member kickMember = findMember.get();

        Optional<JoinRoom> findJoinRoom = room.getJoinRoomList().stream().filter(x -> x.getMember().equals(kickMember)).findFirst();

        if (findJoinRoom.isEmpty()) throw new AuthorizationException(response, "참여자가 아닙니다.");

        JoinRoom joinRoom = findJoinRoom.get();
        roomService.deleteJoinRoom(joinRoom);

        return kickMember;
    }
    
}
