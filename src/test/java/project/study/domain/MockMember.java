package project.study.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.study.enums.MemberStatusEnum;
import project.study.enums.SocialEnum;
import project.study.jpaRepository.BasicJpaRepository;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.PhoneJpaRepository;
import project.study.jpaRepository.SocialJpaRepository;

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
    private BCryptPasswordEncoder encoder;

    private static int index = 0;
    private static final String nickname = "테스트닉네임";
    private static final String name = "테스트이름";
    private static final String account = "test";
    private static final String password = "!@#QWEasd";

    public Member createBasicMember(MemberStatusEnum memberStatusEnum) {
        Member saveMember = Member.builder()
            .memberNickname(nickname + index)
            .memberName(name)
            .memberStatus(memberStatusEnum)
            .memberCreateDate(LocalDateTime.now())
            .build();
        memberJpaRepository.save(saveMember);

        String salt = UUID.randomUUID().toString().substring(0, 6);

        Basic saveBasic = Basic.builder()
            .member(saveMember)
            .account(account + index)
            .password(encoder.encode(password + index + salt))
            .salt(salt)
            .build();
        basicJpaRepository.save(saveBasic);

        index++;
//        return new MockMemberBuilder(saveMember);
        return saveMember;
    }


    public class MockMemberBuilder {

        private Member member;

        public MockMemberBuilder(Member member) {
            this.member = member;
        }

        public Member build() {
            return member;
        }
    }
}
