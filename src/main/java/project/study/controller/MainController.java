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
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.dto.room.ResponsePrivateRoomInfoDto;
import project.study.dto.room.ResponseRoomMemberList;
import project.study.service.RoomService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberAuthorizationCheck memberAuthorizationCheck;
    private final RoomService roomService;

    @GetMapping("/room/{room}")
    public String joinRoom(@SessionLogin(required = true) Member member, @PathRoom("room") Room room, HttpServletResponse response, Model model){
        CommonMember commonMember = memberAuthorizationCheck.getCommonMember(response, member);
        commonMember.joinRoom(new RequestJoinRoomDto(member, room, response, null));

        List<ResponseRoomMemberList> memberList = roomService.getResponseRoomMemberList(room);

        model.addAttribute("max", room.getRoomLimit());
        model.addAttribute("memberList", memberList);

        return "room";
    }

    @GetMapping("/room/{room}/private")
    public String roomPrivate(@SessionLogin(required = true) Member member, @PathRoom("room") Room room, Model model) {
        if (room.isPublic()) {
            return "redirect:/";
        }
        ResponsePrivateRoomInfoDto data = roomService.getResponsePrivateRoomInfoDto(room);
        model.addAttribute("room", data);
        return "room_private";
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
