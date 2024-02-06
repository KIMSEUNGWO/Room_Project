package project.study.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.login.DefaultMemberFactory;
import project.study.dto.login.MemberFactory;
import project.study.dto.login.requestdto.RequestDefaultLoginDto;
import project.study.dto.login.requestdto.RequestDefaultSignupDto;
import project.study.jpaRepository.BasicJpaRepository;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.repository.FreezeRepository;
import project.study.service.LoginService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> defaultLogin(@RequestBody RequestDefaultLoginDto data, HttpServletRequest request) {
        System.out.println("data = " + data);
        HttpSession session = request.getSession();
        loginService.login(data, session);

        return null;

    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> defaultSignup(@RequestBody RequestDefaultSignupDto data) {
        loginService.signup(data);

        return null;
    }
}
