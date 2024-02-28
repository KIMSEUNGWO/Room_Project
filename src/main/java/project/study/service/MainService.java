package project.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.study.controller.api.kakaologin.KakaoLoginRepository;
import project.study.controller.api.kakaologin.KakaoLoginService;
import project.study.domain.Member;
import project.study.domain.Social;
import project.study.domain.SocialToken;
import project.study.dto.login.KakaoMemberFactory;
import project.study.dto.login.MemberFactory;
import project.study.enums.SocialEnum;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.PhoneJpaRepository;
import project.study.jpaRepository.SocialJpaRepository;
import project.study.jpaRepository.SocialTokenJpaRepository;
import project.study.repository.FreezeRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final KakaoLoginService kakaoLoginService;

    public String logout(Member member) {
        Social social = member.getSocial();
        SocialEnum socialType = social.getSocialType();

        System.out.println("socialType = " + socialType);
        if (SocialEnum.KAKAO.equals(socialType)) {
            return kakaoLoginService.logout(member);
        }
        return "/";
    }
}
