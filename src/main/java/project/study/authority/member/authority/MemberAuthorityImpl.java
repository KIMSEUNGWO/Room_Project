package project.study.authority.member.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.authority.member.dto.ResponseMyRoomListDto;
import project.study.domain.Member;
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
    public List<ResponseMyRoomListDto> getMyRoomList(Member member) {
        return roomService.getMyRoomList(member);
    }
}
