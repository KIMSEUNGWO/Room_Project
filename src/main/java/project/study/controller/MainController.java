package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.study.authority.member.CommonMember;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.constant.WebConst;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;

import java.util.List;

import static project.study.constant.WebConst.REQUIRE_LOGIN;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberAuthorizationCheck memberAuthorizationCheck;

    @GetMapping("/room/{room}")
    public String roomCreate(@SessionLogin(required = true) Member member, @PathRoom("room") Room room, HttpServletResponse response){
        CommonMember commonMember = memberAuthorizationCheck.getCommonMember(response, member);
        commonMember.joinRoom(new RequestJoinRoomDto(member, room, response, null));
        return "room";
    }

    @GetMapping("/")
    public String main(@SessionLogin Member member, Model model, HttpServletResponse response){
        if (member != null) {
            CommonMember commonMember = memberAuthorizationCheck.getCommonMember(response, member);
            List<ResponseRoomListDto> myRoomList = commonMember.getMyRoomList(member);
            model.addAttribute("myRoomList", myRoomList);
        }

        return "main";
    }
}
