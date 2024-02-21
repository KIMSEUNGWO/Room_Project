package project.study.authority.member;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.authority.ManagerAuthority;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.*;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ManagerMember implements MemberAuthority, ManagerAuthority {

    private final MemberAuthority memberAuthority;
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
    public void editNotice(Room room, RequestNoticeDto data) {
        managerAuthority.editNotice(room, data);
    }
    @Override
    public void kickMember(Room room, RequestKickDto data) {
        managerAuthority.kickMember(room, data);
    }
    @Override
    public Long createRoom(Member member, RequestCreateRoomDto data) {
        return memberAuthority.createRoom(member, data);
    }
    @Override
    public void notify(Member member, RequestNotifyDto data) {
        memberAuthority.notify(member, data);
    }

    @Override
    public List<ResponseRoomListDto> getMyRoomList(Member member) {
        return memberAuthority.getMyRoomList(member);
    }

    @Override
    public void deleteRoom(Room room, RequestDeleteRoomDto data) {
        managerAuthority.deleteRoom(room, data);
    }

    @Override
    public void joinRoom(RequestJoinRoomDto data) {
        memberAuthority.joinRoom(data);
    }

    @Override
    public void exitRoom(Member member, Room room, HttpServletResponse response) {
        memberAuthority.exitRoom(member, room, response);
    }
}
