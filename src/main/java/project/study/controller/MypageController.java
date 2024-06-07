package project.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.study.config.outh.PrincipalDetails;
import project.study.config.outh.UserRefreshProvider;
import project.study.customAnnotation.CallType;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.mypage.RequestChangePasswordDto;
import project.study.dto.mypage.RequestDeleteMemberDto;
import project.study.dto.mypage.RequestEditInfoDto;
import project.study.service.JoinRoomService;
import project.study.service.MypageService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final MypageService mypageService;
    private final JoinRoomService joinRoomService;
    private final UserRefreshProvider provider;

    @PostMapping("/member/editInfo")
    public ResponseEntity<ResponseDto> editInfo(@AuthenticationPrincipal PrincipalDetails user,
                                                @ModelAttribute RequestEditInfoDto data) {
        Member member = user.getMember();
        mypageService.editInfo(member, data);
        provider.refresh(user);
        return ResponseEntity.ok(new ResponseDto("변경 완료"));
    }

    @PostMapping("/member/delete")
    public ResponseEntity<ResponseDto> deleteMember(@AuthenticationPrincipal PrincipalDetails user,
                                                    @RequestBody RequestDeleteMemberDto data) {
        Member member = user.getMember();
        mypageService.deleteMember(member, data);
        member.getJoinRoomList().iterator().forEachRemaining(joinRoomService::exitRoom);
        provider.refresh(user);
        return ResponseEntity.ok(new ResponseDto("탈퇴가 완료되었습니다."));
    }

    @PostMapping("/change/password")
    public ResponseEntity<ResponseDto> changePassword(@AuthenticationPrincipal PrincipalDetails user,
                                                      @RequestBody RequestChangePasswordDto data) {
        Member member = user.getMember();
        mypageService.changePassword(member, data);
        provider.refresh(user);
        return ResponseEntity.ok(new ResponseDto("변경 완료"));
    }


}
