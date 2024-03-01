package project.study.domain;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.study.enums.MemberStatusEnum;
import project.study.enums.SocialEnum;
import project.study.jpaRepository.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MockMember {

    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private PhoneJpaRepository phoneJpaRepository;
    @Autowired
    private BasicJpaRepository basicJpaRepository;
    @Autowired
    private SocialJpaRepository socialJpaRepository;
    @Autowired
    private FreezeJpaRepository freezeJpaRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private BCryptPasswordEncoder encoder;

    private static int index = 0;
    private static final String nickname = "테스트닉네임";
    private static final String name = "테스트이름";
    private static final String account = "test";
    private static final String password = "!@#QWEasd";

    public Member createBasicMember(MemberStatusEnum memberStatusEnum) {
        Member saveMember = getSaveMember(memberStatusEnum);
        memberJpaRepository.save(saveMember);

        String salt = UUID.randomUUID().toString().substring(0, 6);

        Basic saveBasic = Basic.builder()
            .member(saveMember)
            .account(account + index)
            .password(encoder.encode(password + index + salt))
            .salt(salt)
            .build();
        basicJpaRepository.save(saveBasic);

        if (memberStatusEnum == MemberStatusEnum.이용정지) {
            Freeze saveFreeze = Freeze.builder()
                .member(saveMember)
                .freezeEndDate(LocalDateTime.now().plusMonths(1))
                .freezeReason("테스트를 위한 이용정지")
                .build();
            freezeJpaRepository.save(saveFreeze);
        }


        index++;
        em.flush();
        em.clear();
        return memberJpaRepository.findById(saveMember.getMemberId()).get();
    }

    public Member createSocialMember(MemberStatusEnum memberStatusEnum, SocialEnum socialType) {
        Member saveMember = getSaveMember(memberStatusEnum);
        memberJpaRepository.save(saveMember);


        Social saveSocial = Social.builder()
            .member(saveMember)
            .socialType(socialType)
            .socialEmail(account + index + "@naver.com")
            .build();

        socialJpaRepository.save(saveSocial);

        if (memberStatusEnum == MemberStatusEnum.이용정지) {
            Freeze saveFreeze = Freeze.builder()
                .member(saveMember)
                .freezeEndDate(LocalDateTime.now().plusMonths(1))
                .freezeReason("테스트를 위한 이용정지")
                .build();
            freezeJpaRepository.save(saveFreeze);
        }
        index++;
        em.flush();
        em.clear();
        return memberJpaRepository.findById(saveMember.getMemberId()).get();
    }

    private static Member getSaveMember(MemberStatusEnum memberStatusEnum) {
        return Member.builder()
            .memberNickname(nickname + index)
            .memberName(name)
            .memberExpireDate((memberStatusEnum == MemberStatusEnum.탈퇴) ? LocalDateTime.now() : null)
            .memberStatus(memberStatusEnum)
            .memberCreateDate(LocalDateTime.now())
            .build();
    }


}
