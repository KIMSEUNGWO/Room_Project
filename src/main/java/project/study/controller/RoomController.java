package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.study.authority.member.CommonMember;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.dto.room.ResponseCreateRoomDto;
import project.study.dto.room.SearchRoomListDto;
import project.study.service.RoomService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
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


}
