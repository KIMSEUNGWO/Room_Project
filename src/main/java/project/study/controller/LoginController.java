package project.study.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.study.constant.WebConst;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.login.requestdto.RequestDefaultLoginDto;
import project.study.dto.login.requestdto.RequestDefaultSignupDto;
import project.study.service.LoginService;
import project.study.service.SignupService;

import static project.study.constant.WebConst.LOGIN_MEMBER;
import static project.study.constant.WebConst.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final SignupService signupService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> defaultLogin(@RequestBody RequestDefaultLoginDto data, HttpSession session, HttpServletResponse response) {
        System.out.println("data = " + data);
        loginService.login(data, session, response);

        return ResponseEntity.ok(new ResponseDto("로그인 성공"));

    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> defaultSignup(@RequestBody RequestDefaultSignupDto data) {
        System.out.println("data = " + data);
        loginService.signup(data);
        System.out.println("회원가입 종료");

        return ResponseEntity.ok(new ResponseDto("회원가입 성공"));
    }

    @PostMapping("/distinct/account")
    public ResponseEntity<ResponseDto> distinctAccount(@RequestBody String account) {
        System.out.println("account = " + account);
        signupService.distinctAccount(account);

        return ResponseEntity.ok(new ResponseDto("사용할 수 있는 아이디입니다."));
    }
    @PostMapping("/distinct/nickname")
    public ResponseEntity<ResponseDto> distinctNickname(@RequestBody String nickname) {
        System.out.println("nickname = " + nickname);
        signupService.distinctNickname(nickname);

        return ResponseEntity.ok(new ResponseDto("사용할 수 있는 닉네임입니다."));
    }

    @GetMapping("/login/check")
    public ResponseEntity<ResponseDto> hasLogin(@SessionAttribute(value = LOGIN_MEMBER, required = false) Long memberId) {
        if (memberId == null) {
            return ResponseEntity.ok(new ResponseDto("Require Login"));
        }
        return ResponseEntity.ok(new ResponseDto("Login User"));
    }
}
