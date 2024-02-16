package project.study.authority.member.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.service.RoomService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MemberAuthorityImpl implements MemberAuthority{

    private final RoomService roomService;
    @Override
    public Long createRoom(Member member, RequestCreateRoomDto data) {
        roomService.validRoomData(data);
        return roomService.createRoom(data, member);
    }

    @Override
    public void notify(Member member, RequestNotifyDto data) {
        System.out.println("notify 실행");
    }

    @Override
    public List<ResponseRoomListDto> getMyRoomList(Member member) {
        return roomService.getMyRoomList(member);
    }

    @Override
    public JoinRoom joinRoom(Member member, Room room) {
        System.out.println("joinRoom 실행");
        return null;
    }
}
