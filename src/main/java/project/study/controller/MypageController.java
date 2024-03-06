package project.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.study.customAnnotation.CallType;
import project.study.customAnnotation.SessionLogin;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.mypage.RequestChangePasswordDto;
import project.study.dto.mypage.RequestDeleteMemberDto;
import project.study.dto.mypage.RequestEditInfoDto;
import project.study.service.MypageService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final MypageService mypageService;

    @PostMapping("/member/editInfo")
    public ResponseEntity<ResponseDto> editInfo(@SessionLogin(required = true, type = CallType.REST_CONTROLLER) Member member,
                                                @ModelAttribute RequestEditInfoDto data) {
        System.out.println("data = " + data);
        mypageService.editInfo(member, data);

        return ResponseEntity.ok(new ResponseDto("변경 완료"));
    }

    @PostMapping("/member/delete")
    public ResponseEntity<ResponseDto> deleteMember(@SessionLogin(required = true, type = CallType.REST_CONTROLLER) Member member,
                                                    @RequestBody RequestDeleteMemberDto data) {
        System.out.println("회원 탈퇴 로직");
        System.out.println("password = " + data.getPassword());

        mypageService.deleteMember(member, data);

        return ResponseEntity.ok(new ResponseDto("탈퇴가 완료되었습니다."));
    }

    @PostMapping("/change/password")
    public ResponseEntity<ResponseDto> changePassword(@SessionLogin(required = true, type = CallType.REST_CONTROLLER) Member member,
                                                      @RequestBody RequestChangePasswordDto data) {
        System.out.println("member.getMemberNickname() = " + member.getMemberNickname());
        System.out.println("data = " + data);
        mypageService.changePassword(member, data);

        return ResponseEntity.ok(new ResponseDto("변경 완료"));
    }


}
