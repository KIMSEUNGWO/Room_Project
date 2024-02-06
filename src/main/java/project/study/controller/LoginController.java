package project.study.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.login.DefaultMemberFactory;
import project.study.dto.login.MemberFactory;
import project.study.dto.login.RequestDefaultLoginDto;
import project.study.dto.login.RequestDefaultSignupDto;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> defaultLogin(@RequestBody RequestDefaultLoginDto data) {


        return null;

    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> defaultSignup(@RequestBody RequestDefaultSignupDto data) {
        MemberFactory factory = new DefaultMemberFactory();
        factory.signup(data);

        return null;
    }
}
