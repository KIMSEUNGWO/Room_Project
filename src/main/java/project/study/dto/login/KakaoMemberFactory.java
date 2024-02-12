package project.study.dto.login;

import lombok.RequiredArgsConstructor;
import project.study.controller.api.kakaologin.KakaoLoginRepository;
import project.study.dto.login.validator.MemberValidator;
import project.study.dto.login.validator.SocialMemberValidator;
import project.study.jpaRepository.KakaoTokenJpaRepository;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.PhoneJpaRepository;
import project.study.jpaRepository.SocialJpaRepository;

@RequiredArgsConstructor
public class KakaoMemberFactory implements MemberFactory{

    private final KakaoLoginRepository kakaoLoginRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final SocialJpaRepository socialJpaRepository;
    private final KakaoTokenJpaRepository kakaoTokenJpaRepository;
    private final PhoneJpaRepository phoneJpaRepository;
    @Override
    public MemberInterface createMember() {
        return new KakaoMember(kakaoLoginRepository, memberJpaRepository, socialJpaRepository, kakaoTokenJpaRepository, phoneJpaRepository);
    }

    @Override
    public MemberValidator validator() {
        return new SocialMemberValidator(socialJpaRepository, memberJpaRepository);
    }
}
