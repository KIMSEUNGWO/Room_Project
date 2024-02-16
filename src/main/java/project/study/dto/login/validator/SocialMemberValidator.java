package project.study.dto.login.validator;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.study.domain.Freeze;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestSignupDto;
import project.study.dto.login.requestdto.RequestSocialSignupDto;
import project.study.exceptions.kakaologin.AlreadySignupMemberException;
import project.study.exceptions.kakaologin.SocialException;
import project.study.exceptions.login.FreezeMemberLoginException;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.jpaRepository.SocialJpaRepository;
import project.study.repository.FreezeRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
public class SocialMemberValidator implements MemberValidator {

    private final FreezeRepository freezeRepository;
    private final SocialJpaRepository socialJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    @Override
    public void validLogin(Member member, HttpServletResponse response) {
        // 이용정지 회원인지 확인
        if(!member.isFreezeMember()) return; // 이용정지된 회원이 아님
        if (member.isExpireMember()) throw new SocialException(response, "탈퇴한 회원입니다."); // 탈퇴한 회원인지 확인

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
        throw new SocialException(response, combineMessage(endDate, reason)); // 모든 조건에 걸리지 않은 회원은 이용정지 회원임.
    }

    private String combineMessage(LocalDateTime endDate, String reason) {
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
