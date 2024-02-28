package project.study.dto.login.validator;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.study.domain.Freeze;
import project.study.domain.Member;
import project.study.dto.login.requestdto.RequestDefaultSignupDto;
import project.study.dto.login.requestdto.RequestSignupDto;
import project.study.dto.login.responsedto.Error;
import project.study.dto.login.responsedto.ErrorList;
import project.study.dto.login.responsedto.ResponseSignupdto;
import project.study.exceptions.login.ExpireMemberLoginException;
import project.study.exceptions.login.FreezeMemberLoginException;
import project.study.exceptions.signup.SignupException;
import project.study.jpaRepository.BasicJpaRepository;
import project.study.jpaRepository.MemberJpaRepository;
import project.study.repository.FreezeRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@RequiredArgsConstructor
public class DefaultMemberValidator implements MemberValidator {


    private final FreezeRepository freezeRepository;
    private final BasicJpaRepository basicJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void validLogin(Member member, HttpServletResponse response) {
        System.out.println("회원 검증 로직 시작");
        // 이용정지 회원인지 확인
        if (member.isExpireMember()) throw new ExpireMemberLoginException(); // 탈퇴한 회원인지 확인
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
        RequestDefaultSignupDto data = (RequestDefaultSignupDto) signupDto;

        ErrorList errorList = new ErrorList();

        validAccountDistinct(errorList, data.getAccount());
        validPassword(errorList, data.getPassword());
        validPasswordCheck(errorList, data.getPassword(), data.getPasswordCheck());
        validName(errorList, data.getName());
        validNickName(errorList, data.getNickName());

        if (errorList.hasError()) {
            throw new SignupException(new ResponseSignupdto("error", "회원가입 오류", errorList.getErrorList()));
        }

    }

    private void validNickName(ErrorList errorList, String nickName) {
        if (nickName == null || nickName.length() == 0) {
            Error error = new Error("nickname", "닉네임을 입력해주세요.");
            errorList.addError(error);
            return;
        }

        // 특수문자 여부 확인
        if (isIncludeSpecialCharacter(nickName)) {
            Error error = new Error("nickname", "특수문자를 사용할 수 없습니다.");
            errorList.addError(error);
            return;
        }

        // 중복확인
        boolean distinctNickname = memberJpaRepository.existsByMemberNickname(nickName);
        if (distinctNickname) {
            Error error = new Error("nickname", "이미 사용중인 닉네임입니다.");
            errorList.addError(error);
            return;
        }
    }

    private void validName(ErrorList errorList, String name) {
        if (name == null || name.length() == 0) {
            Error error = new Error("name", "이름을 입력해주세요.");
            errorList.addError(error);
            return;
        }
        if (isIncludeSpecialCharacter(name)) {
            Error error = new Error("name", "특수문자를 사용할 수 없습니다.");
            errorList.addError(error);
            return;
        }
    }

    private void validPasswordCheck(ErrorList errorList, String password, String passwordCheck) {
        // 빈 문자열 확인
        if (password == null || password.length() == 0) {
            return;
        }
        if (passwordCheck == null || passwordCheck.length() == 0) {
            Error error = new Error("passwordCheck", "비밀번호를 한번 더 입력해주세요.");
            errorList.addError(error);
            return;
        }

        // 비밀번호와 비밀번호확인 문자열이 일치하는지 확인
        if (!password.equals(passwordCheck)) {
            Error error = new Error("passwordCheck", "비밀번호가 일치하지 않습니다.");
            errorList.addError(error);
            return;
        }
    }

    private void validPassword(ErrorList errorList, String password) {
        // 빈 문자열 확인
        if (password == null || password.length() == 0) {
            Error error = new Error("password", "비밀번호를 입력해주세요.");
            errorList.addError(error);
            return;
        }

        // 8자 이상 대,소문자, 숫자, 특수문자(!@#$%) 포함되었는지 확인
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%])[A-Za-z\\d!@#$%]{8,}$"; // 비밀번호 정규식
        boolean matches = password.matches(regex);
        if (!matches) {
            Error error = new Error("password", "8자 이상 대,소문자, 숫자, 특수문자(!@#$%)를 포함해야 합니다.");
            errorList.addError(error);
            return;
        }
    }

    private void validAccountDistinct(ErrorList errorList, String account) {
        // 빈문자열인지 확인
        if (account == null || account.length() == 0) {
            errorList.addError(new Error("account", "아이디를 입력해주세요."));
            return;
        }
        // 아이디가 5 ~ 15자의 영문, 숫자로만 이루어져있는지 확인
        if (account.length() < 5 || account.length() > 15) {
            errorList.addError(new Error("account", "5자 이상 15자 이하의 영문,숫자만 가능합니다."));
            return;
        }

        // 모든 특수문자 포함 여부 확인
        if (isIncludeSpecialCharacter(account)) {
            errorList.addError(new Error("account", "특수문자는 사용할 수 없습니다."));
            return;
        }

        // 아이디 중복 확인
        boolean distinctAccount = basicJpaRepository.existsByAccount(account);
        if (distinctAccount) {
            errorList.addError(new Error("account", "이미 사용중인 아이디입니다."));
        }
    }

    // 특수문자가 포함된 문자열인지 확인
    private boolean isIncludeSpecialCharacter(String str) {
        String regex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(str);
        return matcher.matches();
    }
}
