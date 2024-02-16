package project.study.authority.member.authority;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.authority.member.dto.RequestNotifyDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.service.JoinRoomService;
import project.study.service.RoomService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberAuthorityImpl implements MemberAuthority{

    private final RoomService roomService;
    private final JoinRoomService joinRoomService;
    @Override
    public Long createRoom(Member member, RequestCreateRoomDto data) {
        roomService.validMaxCreateRoom(member);
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
    public void joinRoom(RequestJoinRoomDto data) {
        Member member = data.getMember();
        Room room = data.getRoom();
        HttpServletResponse response = data.getResponse();

        // 이미 참여한 회원인지 확인
        boolean alreadyParticipated = joinRoomService.exitsByMemberAndRoom(member, room);
        if (alreadyParticipated) return;

        // 멤버의 최대 참여수 확인
        joinRoomService.validMaxJoinRoom(member, response);

        // 비공개방인지 확인
        if (!room.isPublic()) {

            execute(response, room.getRoomId());
            return;
        }



        System.out.println("joinRoom 실행");
        return;
    }

    private String execute(HttpServletResponse response, Long roomId) {
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String command = "<script> window.location.href='/room/" + roomId+ "/private'; </script>";
        try (PrintWriter out = response.getWriter()) {
            out.println(command);
            out.flush();
        } catch (IOException e) {
        }
        return null;
    }
}
