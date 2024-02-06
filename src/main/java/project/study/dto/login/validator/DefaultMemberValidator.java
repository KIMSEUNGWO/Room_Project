package project.study.dto.login.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project.study.collection.PasswordManager;
import project.study.domain.Basic;
import project.study.domain.Freeze;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.login.requestdto.RequestDefaultLoginDto;
import project.study.dto.login.requestdto.RequestLoginDto;
import project.study.dto.login.requestdto.RequestSignupDto;
import project.study.exceptions.login.ExpireMemberLoginException;
import project.study.exceptions.login.FreezeMemberLoginException;
import project.study.exceptions.login.InvalidLoginException;
import project.study.jpaRepository.BasicJpaRepository;
import project.study.jpaRepository.FreezeJpaRepository;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.repository.FreezeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DefaultMemberValidator implements MemberValidator {

    private final FreezeRepository freezeRepository;

    @Override
    public void validLogin(Member member) {
        checkFreezeMember(member); // 이용정지 회원인지 확인
        checkExpireMember(member); // 탈퇴한 회원인지 확인
    }

    private void checkExpireMember(Member member) {
        if (member.isExpireMember()) throw new ExpireMemberLoginException();
    }

    private void checkFreezeMember(Member member) {
        if(!member.isFreezeMember()) return; // 이용정지된 회원이 아님

        Optional<Freeze> findFreeze = freezeRepository.findByMemberId(member.getMemberId());
        if (findFreeze.isEmpty()) return; // Freeze Entity 없음 ( 혹시 모를 예외 처리 )

        Freeze freeze = findFreeze.get();
        if (freeze.isFinish()) { // Freeze Entity는 존재하지만 이용정지 기간에 풀린 경우
            freezeRepository.delete(freeze);
            member.changeStatusToNormal();
            return;
        }
        LocalDateTime endDate = freeze.getFreezeEndDate();
        String reason = freeze.getFreezeReason();

        throw new FreezeMemberLoginException(combineMessage(endDate, reason)); // 모든 조건에 걸리지 않은 회원은 이용정지 회원임.
    }

    public String combineMessage(LocalDateTime endDate, String reason) {
        int year = endDate.getYear();
        int month = endDate.getMonthValue();
        int day = endDate.getDayOfMonth();
        int hour = endDate.getHour();
        int minute = endDate.getMinute();

        String time = String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
        return "이용이 정지된 회원입니다. \n ~ " + time + " 까지 \n" + "사유 : " + reason;
    }

    @Override
    public void validSignup(RequestSignupDto signupDto) {

    }
}
