package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.study.authority.member.CommonMember;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.authority.member.dto.RequestCreateRoomDto;
import project.study.dto.room.ResponseCreateRoomDto;
import project.study.service.RoomService;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final MemberAuthorizationCheck authorizationCheck;

    @PostMapping(value = "/room/create")
    public ResponseEntity<ResponseDto> createRoom(@SessionLogin Member member, @ModelAttribute RequestCreateRoomDto data, HttpServletResponse response) {
        System.out.println("data = " + data);

        CommonMember commonMember = authorizationCheck.getCommonMember(response, member);
        Long roomId = commonMember.createRoom(member, data);

        String redirectURI = "/room/" + roomId;
        return new ResponseEntity<>(new ResponseCreateRoomDto("ok", "방 생성 완료", redirectURI), HttpStatus.OK);
    }


}
