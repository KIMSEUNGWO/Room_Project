package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.authority.ManagerAuthority;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.RequestEditRoomDto;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.chat.dto.ResponseChatHistory;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.domain.RoomPassword;
import project.study.dto.abstractentity.ResponseDto;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.dto.abstractentity.ResponseObject;
import project.study.dto.room.*;
import project.study.exceptions.RestFulException;
import project.study.service.JoinRoomService;
import project.study.service.RoomService;

import java.util.List;

import static project.study.constant.WebConst.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final MemberAuthorizationCheck authorizationCheck;
    private final RoomService roomService;
    private final JoinRoomService joinRoomService;

    @PostMapping(value = "/room/create")
    public ResponseEntity<ResponseDto> createRoom(@SessionLogin(required = true) Member member, @ModelAttribute RequestCreateRoomDto data, HttpServletResponse response) {
        System.out.println("data = " + data);

        MemberAuthority commonMember = authorizationCheck.getMemberAuthority(response, member);
        Long roomId = commonMember.createRoom(member, data);

        String redirectURI = "/room/" + roomId;
        return ResponseEntity.ok(new ResponseObject<>("방 생성 완료", redirectURI));
    }

    @GetMapping("/room/{room}/edit")
    public ResponseEntity<ResponseDto> getEditRoomForm(@SessionLogin(required = true) Member member, @PathRoom("room") Room room, HttpServletResponse response) {
        authorizationCheck.getManagerAuthority(response, member, room);

        ResponseEditRoomForm editRoomForm = roomService.getEditRoomForm(room);

        return ResponseEntity.ok(new ResponseObject<>("조회성공", editRoomForm));
    }

    @PostMapping("/room/{room}/edit")
    public ResponseEntity<ResponseDto> editRoom(@SessionLogin(required = true) Member member,
                                                @PathRoom("room") Room room,
                                                HttpServletResponse response,
                                                @ModelAttribute RequestEditRoomDto data) {
        ManagerAuthority managerMember = authorizationCheck.getManagerAuthority(response, member, room);
        System.out.println("RequestEditRoomDto = " + data);
        System.out.println(data.getMax());
        managerMember.editRoom(room, data);
        return ResponseEntity.ok(new ResponseDto("성공"));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, @RequestParam("word") String word, Pageable pageable) {
        System.out.println("word = " + word + " pageable = " + pageable.getPageNumber());

        List<ResponseRoomListDto> roomList = roomService.searchRoomList(memberId, word, pageable);

        for (ResponseRoomListDto data : roomList) {
            System.out.println("data = " + data);
        }

        return ResponseEntity.ok(new SearchRoomListDto("검색성공", word, roomList));
    }

    @PostMapping("/room/{room}/private")
    public ResponseEntity<ResponseDto> roomPrivate(@SessionLogin(required = true) Member member,
                                                   @PathRoom("room") Room room,
                                                   @RequestBody String password,
                                                   HttpServletResponse response) {

        if (room.isPublic() || room.getRoomPassword() == null) return ResponseEntity.ok(new ResponseDto(ERROR, "잘못된 접근입니다."));

        RoomPassword rp = room.getRoomPassword();
        if (!rp.compareRoomPassword(password)) {
            return ResponseEntity.ok(new ResponseDto("invalidPassword", "비밀번호가 일치하지 않습니다."));
        }
        MemberAuthority commonMember = authorizationCheck.getMemberAuthority(response, member);
        commonMember.joinRoom(new RequestJoinRoomDto(member, room, response, password));

        return ResponseEntity.ok(new ResponseDto("/room/" + room.getRoomId()));
    }

    @GetMapping("/room/{room}/history")
    public ResponseEntity<ResponseDto> chatHistory(@PathRoom("room") Room room) {
        // JoinRoom 검증 안되어있음 아직.

        List<ResponseChatHistory> history = roomService.findByChatHistory(room);
        return ResponseEntity.ok(new ResponseObject<>("성공", history));
    }

    @GetMapping("/room/{room}/notice")
    public ResponseEntity<ResponseDto> roomNotice(@SessionAttribute(name = LOGIN_MEMBER, required = false) Long memberId, @PathRoom("room") Room room) {

        boolean exitsByMemberAndRoom = joinRoomService.exitsByMemberAndRoom(memberId, room);
        if (!exitsByMemberAndRoom) throw new RestFulException(new ResponseDto(ERROR, "권한 없음"));

        ResponseRoomNotice responseRoomNotice = roomService.getNotice(room);

        return ResponseEntity.ok(new ResponseObject<>("성공", responseRoomNotice));
    }


}
