package project.study.dto.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.study.common.CustomDateTime;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestDefaultLoginDto;
import project.study.dto.login.requestdto.RequestDefaultSignupDto;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;
import project.study.enums.MemberStatusEnum;
import project.study.enums.Role;
import project.study.exceptions.login.InvalidLoginException;
import project.study.jpaRepository.MemberJpaRepository;


@RequiredArgsConstructor
@Slf4j
public class DefaultMember implements MemberInterface {

    private final MemberJpaRepository memberJpaRepository;
    private final BCryptPasswordEncoder encoder;


    @Transactional
    @Override
    public Member signup(RequestSignupDto signupDto) {
        RequestDefaultSignupDto data = (RequestDefaultSignupDto) signupDto;

        Member saveMember = Member.builder()
                .account(data.getAccount())
                .password(encoder.encode(data.getPassword()))
                .memberName(data.getName())
                .memberNickname(data.getNickName())
                .memberCreateDate(CustomDateTime.now())
                .memberStatus(MemberStatusEnum.정상)
                .role(Role.USER)
                .build();
        return memberJpaRepository.save(saveMember);
    }

    @Override
    public Member login(RequestLoginDto loginDto) {
        RequestDefaultLoginDto data = (RequestDefaultLoginDto) loginDto;

        Member member = checkAccount(data);
        checkPassword(member, data);

        return member;
    }

    @Override
    public String logout(Member member) {

        return "/";
    }

    private Member checkAccount(RequestDefaultLoginDto data) {
        return memberJpaRepository.findByAccount(data.getAccount()).orElseThrow(InvalidLoginException::new);
    }

    private void checkPassword(Member member, RequestDefaultLoginDto data) {
        boolean validPassword = member.isValidPassword(encoder, data.getPassword());
        if (!validPassword) throw new InvalidLoginException(); // 비밀번호가 일치하지 않음
    }
}
