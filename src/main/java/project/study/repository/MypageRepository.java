package project.study.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.study.collection.PasswordManager;
import project.study.constant.WebConst;
import project.study.domain.Basic;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.login.responsedto.Error;
import project.study.dto.login.responsedto.ErrorList;
import project.study.dto.mypage.RequestChangePasswordDto;
import project.study.exceptions.RestFulException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MypageRepository {

    private final BCryptPasswordEncoder encoder;

    public void valid(Member member, RequestChangePasswordDto data, ErrorList errorList) {
        if (member.isSocialMember()) {
            throw new RestFulException(new ResponseDto(WebConst.ERROR, "소셜회원은 비밀번호를 변경할 수 없습니다."));
        }
        String nowPassword = data.getNowPassword();
        String changePassword = data.getChangePassword();
        String checkPassword = data.getCheckPassword();

        if (nowPassword == null) {
            errorList.addError(new Error("bfpw", "현재 비밀번호를 입력해주세요."));
        }
        if (changePassword == null) {
            errorList.addError(new Error("cpw", "변경할 비밀번호를 입력해주세요."));
        }
        if (checkPassword == null) {
            errorList.addError(new Error("cpwc", "변경할 비밀번호는 한번 더 입력해주세요."));
        }
        if (errorList.hasError()) return;

        PasswordManager passwordManager = new PasswordManager(member.getBasic(), encoder);
        boolean matches = passwordManager.isValidPassword(nowPassword);
        if (!matches) {
            errorList.addError(new Error("bfpw", "비밀번호가 일치하지 않습니다."));
            return;
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%])[A-Za-z\\d!@#$%]{8,}$"; // 비밀번호 정규식
        if (!changePassword.matches(regex)) {
            errorList.addError(new Error("cpw", "8자 이상 대,소문자, 숫자, 특수문자(!@#$%)를 포함해야 합니다."));
            return;
        }

        if (!changePassword.equals(checkPassword)) {
            errorList.addError(new Error("cpw", "변경할 비밀번호가 일치하지 않습니다."));
        }

    }

    @Transactional
    public void changePassword(Member member, RequestChangePasswordDto data) {
        Basic basic = member.getBasic();
        basic.changePassword(encoder.encode(data.getChangePassword() + basic.getSalt()));
    }
}
