package project.study.authority.member.authority;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.study.authority.member.dto.*;
import project.study.constant.WebConst;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.domain.RoomNotice;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.room.ResponseRoomNotice;
import project.study.exceptions.RestFulException;
import project.study.exceptions.authority.AuthorizationException;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.service.JoinRoomService;
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
    public Member managerEntrust(Member member, Room room, RequestEntrustDto data) {
        System.out.println("managerEntrust 실행");
        Optional<Member> findMember = memberJpaRepository.findByMemberNickname(data.getNickname());
        if (findMember.isEmpty()) throw new RestFulException(new ResponseDto(WebConst.ERROR, "존재하지 않는 회원입니다."));
        Member nextManagerMember = findMember.get();

        Optional<JoinRoom> nextManagerJoinRoom = room.getJoinRoomList().stream().filter(x -> x.getMember().equals(nextManagerMember)).findFirst();

        if (nextManagerJoinRoom.isEmpty()) throw new RestFulException(new ResponseDto(WebConst.ERROR, "참여자가 아닙니다."));

        Optional<JoinRoom> managerJoinRoom = room.getJoinRoomList().stream().filter(x -> x.getMember().equals(member)).findFirst();
        if (managerJoinRoom.isEmpty()) throw new RestFulException(new ResponseDto(WebConst.ERROR, "권한이 없습니다."));

        JoinRoom currentManager = managerJoinRoom.get();
        JoinRoom nextManager = nextManagerJoinRoom.get();

        currentManager.changeToNotManager();
        nextManager.changeToManager();

        return nextManagerMember;
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
        Optional<Member> findMember = memberJpaRepository.findByMemberNickname(data.getNickname());
        if (findMember.isEmpty()) throw new RestFulException(new ResponseDto(WebConst.ERROR, "존재하지 않는 회원입니다."));
        Member kickMember = findMember.get();

        Optional<JoinRoom> findJoinRoom = room.getJoinRoomList().stream().filter(x -> x.getMember().equals(kickMember)).findFirst();

        if (findJoinRoom.isEmpty()) throw new RestFulException(new ResponseDto(WebConst.ERROR, "참여자가 아닙니다."));

        JoinRoom joinRoom = findJoinRoom.get();
        roomService.deleteJoinRoom(joinRoom);

        return kickMember;
    }
    
}
