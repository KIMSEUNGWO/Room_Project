package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.authority.member.dto.ResponseRoomListDto;
import project.study.config.outh.PrincipalDetails;
import project.study.customAnnotation.CallType;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.JoinRoom;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.dto.MyPageInfo;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.room.ResponseRoomMemberList;
import project.study.dto.room.SearchRoomListDto;
import project.study.service.MainService;
import project.study.service.RoomService;

import java.util.List;

import static project.study.constant.WebConst.LOGIN_MEMBER;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberAuthorizationCheck memberAuthorizationCheck;
    private final RoomService roomService;
    private final MainService mainService;

    @GetMapping("/room/{room}")
    public String joinRoom(@AuthenticationPrincipal PrincipalDetails user, @PathRoom("room") Room room, HttpServletResponse response, Model model){
        Member member = user.getMember();
        MemberAuthority commonMember = memberAuthorizationCheck.getMemberAuthority(response, member);
        JoinRoom joinRoom = commonMember.joinRoom(new RequestJoinRoomDto(member, room, response, null));

        List<ResponseRoomMemberList> memberList = roomService.getResponseRoomMemberList(room, member);
        Room.ResponseRoomInfo roomInfo = room.getResponseRoomInfo(joinRoom);


        model.addAttribute("memberList", memberList);
        model.addAttribute("roomInfo", roomInfo);
        model.addAttribute("hasPhone", member.hasPhone());

        return "room";
    }

    @GetMapping("/room/{room}/private")
    public String roomPrivate(@PathRoom("room") Room room, Model model) {
        if (room.isPublic()) return "redirect:/";

        Room.ResponsePrivateRoomInfoDto data = room.getResponsePrivateRoomInfo();
        model.addAttribute("room", data);
        return "room_private";
    }


    @GetMapping("/")
    public String main(@AuthenticationPrincipal PrincipalDetails user, Model model, HttpServletResponse response){

        if (user != null) {
            Member member = user.getMember();
            MemberAuthority commonMember = memberAuthorizationCheck.getMemberAuthority(response, member);
            List<ResponseRoomListDto> myRoomList = commonMember.getMyRoomList(member);
            model.addAttribute("myRoomList", myRoomList);
            model.addAttribute("profile", member.getStoreImage());
        }

        return "main";
    }

    @ResponseBody
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(@AuthenticationPrincipal PrincipalDetails user, @RequestParam("word") String word, Pageable pageable) {
        List<ResponseRoomListDto> roomList = roomService.searchRoomList(user, word, pageable);
        return ResponseEntity.ok(new SearchRoomListDto("검색성공", word, roomList));
    }

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal PrincipalDetails user, Model model) {
        System.out.println("user = " + user);
        if (user == null) return "redirect:/?redirectURI=/mypage";

        Member member = user.getMember();
        MyPageInfo info = member.getMyPageInfo();

        model.addAttribute("main", info);
        model.addAttribute("profile", member.getStoreImage());
        return "mypage";
    }

    @GetMapping("/logout")
    public String logout(@SessionLogin(type = CallType.CONTROLLER) Member member, HttpSession session) {
        if (member == null) return "redirect:/";

        session.removeAttribute(LOGIN_MEMBER);

        return "redirect:" + mainService.logout(member);
    }

//    @GetMapping("/room/{room}/share")
//    public String share(@SessionLogin(required = true, type = CallType.CONTROLLER) Member member, @PathRoom("room") Room room) {
//        return "room_share";
//    }

}
