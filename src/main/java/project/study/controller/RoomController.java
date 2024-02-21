package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.study.authority.member.CommonMember;
import project.study.authority.member.ManagerMember;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.dto.RequestEditRoomDto;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.chat.component.ChatAccessToken;
import project.study.chat.dto.ResponseChatHistory;
import project.study.constant.WebConst;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final MemberAuthorizationCheck authorizationCheck;
    private final RoomService roomService;
    private final JoinRoomService joinRoomService;
    private final ChatAccessToken chatAccessToken;

    @PostMapping(value = "/room/create")
    public ResponseEntity<ResponseDto> createRoom(@SessionLogin(required = true) Member member, @ModelAttribute RequestCreateRoomDto data, HttpServletResponse response) {
        System.out.println("data = " + data);

        CommonMember commonMember = authorizationCheck.getCommonMember(response, member);
        Long roomId = commonMember.createRoom(member, data);

        String redirectURI = "/room/" + roomId;
        return new ResponseEntity<>(new ResponseCreateRoomDto("ok", "방 생성 완료", redirectURI), HttpStatus.OK);
    }

    @GetMapping("/room/{room}/edit")
    public ResponseEntity<ResponseDto> getEditRoomForm(@SessionLogin(required = true) Member member, @PathRoom("room") Room room, HttpServletResponse response) {
        ManagerMember managerMember = authorizationCheck.getManagerMember(response, member, room);

        ResponseEditRoomForm editRoomForm = roomService.getEditRoomForm(room);

        return new ResponseEntity<>(new ResponseEditRoomFormDto("ok", "조회성공", editRoomForm), HttpStatus.OK);
    }

    @PostMapping("/room/{room}/edit")
    public ResponseEntity<ResponseDto> editRoom(@SessionLogin(required = true) Member member,
                                                @PathRoom("room") Room room,
                                                HttpServletResponse response,
                                                @ModelAttribute RequestEditRoomDto data) {
        ManagerMember managerMember = authorizationCheck.getManagerMember(response, member, room);
        System.out.println("RequestEditRoomDto = " + data);
        managerMember.editRoom(room, data);
        return new ResponseEntity<>(new ResponseDto("ok", "성공"), HttpStatus.OK);
    }

    @PostMapping(value = "/room/exit")
    public ResponseEntity<ResponseDto> exitRoom(@SessionLogin(required = true) Member member, @RequestBody String roomId, HttpServletResponse response) {
        Room room = roomService.findByRoom(roomId, response);

        CommonMember commonMember = authorizationCheck.getCommonMember(response, member);
        commonMember.exitRoom(member, room, response);

        return new ResponseEntity<>(new ResponseDto("ok", "방 생성 완료"), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(@SessionLogin Member member, @RequestParam("word") String word, Pageable pageable) {
        System.out.println("word = " + word);
        System.out.println("pageable = " + pageable.getPageNumber());

        List<ResponseRoomListDto> roomList = roomService.searchRoomList(member, word, pageable);

        for (ResponseRoomListDto data : roomList) {
            System.out.println("data = " + data);
        }

        return new ResponseEntity<>(new SearchRoomListDto("ok", "검색성공", word, roomList), HttpStatus.OK);
    }

    @PostMapping("/room/{room}/private")
    public ResponseEntity<ResponseDto> roomPrivate(@SessionLogin(required = true) Member member,
                                                   @PathRoom("room") Room room,
                                                   @RequestBody String password,
                                                   HttpServletResponse response) {

        if (room.isPublic() || room.getRoomPassword() == null) return new ResponseEntity<>(new ResponseDto("error", "잘못된 접근입니다."), HttpStatus.OK);

        RoomPassword rp = room.getRoomPassword();
        String roomPassword = rp.getRoomPassword();
        if (!roomPassword.equals(password)) {
            return new ResponseEntity<>(new ResponseDto("invalidPassword", "비밀번호가 일치하지 않습니다."), HttpStatus.OK);
        }
        CommonMember commonMember = authorizationCheck.getCommonMember(response, member);
        commonMember.joinRoom(new RequestJoinRoomDto(member, room, response, password));

        return new ResponseEntity<>(new ResponseDto("ok", "/room/" + room.getRoomId()), HttpStatus.OK);
    }

    @GetMapping("/room/{room}/history")
    public ResponseEntity<List<ResponseChatHistory>> chatHistory(@PathRoom("room") Room room) {
        // JoinRoom 검증 안되어있음 아직.

        List<ResponseChatHistory> chatHistory = roomService.findByChatHistory(room);
        return new ResponseEntity<>(chatHistory, HttpStatus.OK);
    }

    @GetMapping("/room/{room}/access")
    public ResponseEntity<ResponseDto> accessToken(@SessionLogin(required = true) Member member, @PathRoom("room") Room room) {
        boolean exitsByMemberAndRoom = joinRoomService.exitsByMemberAndRoom(member, room);
        if (!exitsByMemberAndRoom) throw new RestFulException(new ResponseDto("error", "권한 없음"));

        chatAccessToken.createAccessToken(member.getMemberId(), room.getRoomId());
        return new ResponseEntity<>(new ResponseDto("ok", String.valueOf(member.getMemberId())), HttpStatus.OK);
    }

    @GetMapping("/room/{room}/notice")
    public ResponseEntity<ResponseObject> roomNotice(@SessionLogin(required = true) Member member, @PathRoom("room") Room room) {
        boolean exitsByMemberAndRoom = joinRoomService.exitsByMemberAndRoom(member, room);
        if (!exitsByMemberAndRoom) throw new RestFulException(new ResponseDto("error", "권한 없음"));

        ResponseRoomNotice responseRoomNotice = roomService.getNotice(room);
        return new ResponseEntity<>(new ResponseObject("ok", responseRoomNotice), HttpStatus.OK);
    }


}
