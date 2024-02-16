package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.study.authority.member.CommonMember;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.domain.RoomPassword;
import project.study.dto.abstractentity.ResponseDto;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.dto.room.ResponseCreateRoomDto;
import project.study.dto.room.ResponsePrivateRoomInfoDto;
import project.study.dto.room.SearchRoomListDto;
import project.study.service.RoomService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final MemberAuthorizationCheck authorizationCheck;
    private final RoomService roomService;

    @PostMapping(value = "/room/create")
    public ResponseEntity<ResponseDto> createRoom(@SessionLogin Member member, @ModelAttribute RequestCreateRoomDto data, HttpServletResponse response) {
        System.out.println("data = " + data);

        CommonMember commonMember = authorizationCheck.getCommonMember(response, member);
        Long roomId = commonMember.createRoom(member, data);

        String redirectURI = "/room/" + roomId;
        return new ResponseEntity<>(new ResponseCreateRoomDto("ok", "방 생성 완료", redirectURI), HttpStatus.OK);
    }
    @PostMapping(value = "/room/exit")
    public ResponseEntity<ResponseDto> exitRoom(@SessionLogin Member member, @RequestBody String roomId, HttpServletResponse response) {
        System.out.println("roomId = " + roomId);

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


}
