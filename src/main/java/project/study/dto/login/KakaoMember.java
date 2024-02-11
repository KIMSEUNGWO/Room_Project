package project.study.dto.login;

import lombok.RequiredArgsConstructor;
import project.study.controller.api.kakaologin.KakaoLoginRepository;
import project.study.domain.KakaoToken;
import project.study.domain.Member;
import project.study.domain.Social;
import project.study.dto.login.requestdto.*;
import project.study.enums.MemberStatusEnum;
import project.study.enums.SocialEnum;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.SocialJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class KakaoMember implements MemberInterface{

    private final KakaoLoginRepository kakaoLoginRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SocialJpaRepository socialJpaRepository;

    @Override
    public Member signup(RequestSignupDto signupDto) {
        RequestSocialSignupDto data = (RequestSocialSignupDto) signupDto;

        Member saveMember = Member.builder()
                .memberName(data.getName())
                .memberNickname(data.getNickName())
                .memberCreateDate(LocalDateTime.now())
                .memberStatus(MemberStatusEnum.정상)
                .build();
        memberJpaRepository.save(saveMember);


        Social saveSocial = Social.builder()
                .socialType(SocialEnum.KAKAO)
                .socialEmail(data.getEmail())
                .member(saveMember)
                .build();
        socialJpaRepository.save(saveSocial);

        return saveMember;
    }

    @Override
    public Member login(RequestLoginDto loginDto) {
        RequestSocialLoginDto data = (RequestSocialLoginDto) loginDto;

        KakaoToken kakaoToken = kakaoLoginRepository.getKakaoAccessToken(data.getCode());
        kakaoLoginRepository.getUserInfo(data, kakaoToken.getAccess_token());

        Optional<Social> findMember = kakaoLoginRepository.findBySocialAndEmail(data.getSocialEnum(), data.getEmail());
        if (findMember.isEmpty()) return null;
        Social social = findMember.get();

        return social.getMember();
    }
}
