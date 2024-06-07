package project.study.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.study.authority.member.MemberAuthorizationCheck;
import project.study.authority.member.authority.ManagerAuthority;
import project.study.authority.member.authority.MemberAuthority;
import project.study.authority.member.dto.RequestEditRoomDto;
import project.study.authority.member.dto.RequestJoinRoomDto;
import project.study.chat.ChatService;
import project.study.config.outh.PrincipalDetails;
import project.study.config.outh.UserRefreshProvider;
import project.study.domain.Chat;
import project.study.customAnnotation.PathRoom;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.domain.Room;
import project.study.domain.RoomNotice;
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
@RequestMapping("/room")
public class RoomController {

    private final MemberAuthorizationCheck authorizationCheck;
    private final RoomService roomService;
    private final JoinRoomService joinRoomService;
    private final ChatService chatService;
    private final UserRefreshProvider provider;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> createRoom(@AuthenticationPrincipal PrincipalDetails user,
                                                  @Validated @ModelAttribute RequestCreateRoomDto data,
                                                  BindingResult bindingResult,
                                                  HttpServletResponse response) {
        Member member = user.getMember();
        MemberAuthority commonMember = authorizationCheck.getMemberAuthority(response, member);

        data.valid(bindingResult, "방 생성 에러");
        Long roomId = commonMember.createRoom(member, data);

        String redirectURI = "/room/" + roomId;
        provider.refresh(user);
        return ResponseEntity.ok(new ResponseObject<>("방 생성 완료", redirectURI));
    }

    @GetMapping("/{room}/edit")
    public ResponseEntity<ResponseDto> getEditRoomForm(@AuthenticationPrincipal PrincipalDetails user,
                                                       @PathRoom("room") Room room,
                                                       HttpServletResponse response) {
        Member member = user.getMember();
        authorizationCheck.getManagerAuthority(response, member, room);
        ResponseEditRoomForm editRoomForm = roomService.getEditRoomForm(room);

        provider.refresh(user);
        return ResponseEntity.ok(new ResponseObject<>("조회성공", editRoomForm));
    }

    @PostMapping("/{room}/edit")
    public ResponseEntity<ResponseDto> editRoom(@AuthenticationPrincipal PrincipalDetails user,
                                                @PathRoom("room") Room room,
                                                HttpServletResponse response,
                                                @Validated @ModelAttribute RequestEditRoomDto data,
                                                BindingResult bindingResult) {
        Member member = user.getMember();
        ManagerAuthority managerMember = authorizationCheck.getManagerAuthority(response, member, room);

        data.validEdit(bindingResult, room);
        managerMember.editRoom(room, data);

        provider.refresh(user);
        return ResponseEntity.ok(new ResponseDto("성공"));
    }



    @PostMapping("/{room}/private")
    public ResponseEntity<ResponseDto> roomPrivate(@AuthenticationPrincipal PrincipalDetails user,
                                                   @PathRoom("room") Room room,
                                                   @RequestBody String password,
                                                   HttpServletResponse response) {
        Member member = user.getMember();

        if (room.isPublic() || !room.hasRoomPassword()) return ResponseEntity.badRequest().body(new ResponseDto(ERROR, "잘못된 접근입니다."));

        if (!room.isValidPassword(password)) {
            return ResponseEntity.badRequest().body(new ResponseDto("invalidPassword", "비밀번호가 일치하지 않습니다."));
        }
        MemberAuthority commonMember = authorizationCheck.getMemberAuthority(response, member);
        commonMember.joinRoom(new RequestJoinRoomDto(member, room, response, password));

        return ResponseEntity.ok(new ResponseDto("/room/" + room.getRoomId()));
    }

    @GetMapping("/{room}/history")
    public ResponseEntity<ResponseDto> chatHistory(@PathRoom("room") Room room,
                                                   @RequestParam(name = "token") String token,
                                                   @RequestParam(name = "page", required = false, defaultValue = "0") Long pageValue ) {
        Member member = chatService.getMember(token, room.getRoomId());

        List<Chat.ResponseChatHistory> history = chatService.findByChatHistory(room, pageValue, member.getMemberId());
        return ResponseEntity.ok(new ResponseObject<>("성공", history));
    }

    @GetMapping("/{room}/notice")
    public ResponseEntity<ResponseDto> roomNotice(@AuthenticationPrincipal PrincipalDetails user, @PathRoom("room") Room room) {

        boolean exitsByMemberAndRoom = joinRoomService.exitsByMemberAndRoom(user, room);
        if (!exitsByMemberAndRoom) throw new RestFulException(new ResponseDto(ERROR, "권한 없음"));

        RoomNotice.ResponseRoomNotice responseRoomNotice = room.getChatInsideNotice();

        return ResponseEntity.ok(new ResponseObject<>("성공", responseRoomNotice));
    }

    @GetMapping("/{room}/memberList")
    public ResponseEntity<ResponseDto> roomMemberList(@AuthenticationPrincipal PrincipalDetails user, @PathRoom("room") Room room) {

        boolean exitsByMemberAndRoom = joinRoomService.exitsByMemberAndRoom(user, room);
        if (!exitsByMemberAndRoom) throw new RestFulException(new ResponseDto(ERROR, "권한 없음"));

        List<String> responseRoomMemberList = chatService.findRecentlyHistoryMemberNickname(user.getMember().getMemberId(), room);
        return ResponseEntity.ok(new ResponseObject<>("성공", responseRoomMemberList));
    }


}
