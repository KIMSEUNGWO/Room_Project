package project.study.dto.login.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;
import project.study.dto.login.requestdto.RequestSocialSignupDto;
import project.study.exceptions.kakaologin.AlreadySignupMemberException;
import project.study.exceptions.kakaologin.SocialException;
import project.study.jpaRepository.BasicJpaRepository;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.SocialJpaRepository;

import java.rmi.AlreadyBoundException;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
public class SocialMemberValidator implements MemberValidator {

    private final SocialJpaRepository socialJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    @Override
    public void validLogin(Member member) {

    }

    @Override
    public void validSignup(RequestSignupDto signupDto) {
        RequestSocialSignupDto data = (RequestSocialSignupDto) signupDto;

        // 중복 가입인 경우
        boolean distinctEmail = socialJpaRepository.existsBySocialEmail(data.getEmail());
        if (distinctEmail) {
            throw new AlreadySignupMemberException(data.getResponse());
        }

        // 닉네임이 중복이라면 닉네임 + #숫자랜덤5자리 로 생성 중복이 아닐때까지 while 반복
        String nickname = data.getNickName();
        while (true) {
            boolean distinctNickname = memberJpaRepository.existsByMemberNickname(nickname);
            if (!distinctNickname) break;

            String random = "#" + new Random().ints(0, 9).limit(5).toString();
            int index = nickname.indexOf("#");
            if (index == -1) {
                nickname += random;
            } else {
                nickname = nickname.substring(0, index) + random;
            }
        }
        data.setNickName(nickname);


        String phone = data.getPhone();
        // +82 10-1234-5678 -> 01012345678 문자열 변경
        if (phone.contains("+")) {
            phone = phone.replaceAll("(^\\+[0-9]+ )", "0").replace("-", "");
            data.setPhone(phone);
        }


    }
}
