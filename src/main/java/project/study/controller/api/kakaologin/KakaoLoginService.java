package project.study.controller.api.kakaologin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.study.domain.Member;
import project.study.dto.login.KakaoMemberFactory;
import project.study.dto.login.MemberFactory;
import project.study.dto.login.requestdto.RequestSocialLoginDto;
import project.study.dto.login.requestdto.RequestSocialSignupDto;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.SocialJpaRepository;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final KakaoLoginRepository kakaoLoginRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SocialJpaRepository socialJpaRepository;

    public void login(String code, HttpSession session) {
        RequestSocialLoginDto data = new RequestSocialLoginDto(code);
        RequestSocialSignupDto signupDto = new RequestSocialSignupDto(data);

        MemberFactory factory = new KakaoMemberFactory(kakaoLoginRepository, memberJpaRepository, socialJpaRepository);
        Member loginMember = factory.login(data, session);
        if (loginMember == null) {
            factory.signup(signupDto);
            factory.login(data, session);
        }
    }
}
