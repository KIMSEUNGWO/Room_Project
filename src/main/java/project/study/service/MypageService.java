package project.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.collection.PasswordManager;
import project.study.constant.WebConst;
import project.study.controller.image.FileUpload;
import project.study.controller.image.FileUploadType;
import project.study.domain.Member;
import project.study.dto.abstractentity.ResponseDto;
import project.study.dto.abstractentity.ResponseObject;
import project.study.dto.login.responsedto.ErrorList;
import project.study.dto.mypage.RequestChangePasswordDto;
import project.study.dto.mypage.RequestEditInfoDto;
import project.study.enums.MemberStatusEnum;
import project.study.exceptions.RestFulException;
import project.study.repository.MypageRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MypageService {

    private final MypageRepository mypageRepository;

    private final FileUpload fileUpload;
    private final SignupService signupService;


    @Transactional
    public void editInfo(Member member, RequestEditInfoDto data) {
        signupService.distinctNickname(data.getNickname());

        fileUpload.editFile(data.getProfile(), FileUploadType.MEMBER_PROFILE, member);

        member.updateInfo(data);
    }

    public void changePassword(Member member, RequestChangePasswordDto data) {
        ErrorList errorList = new ErrorList();
        mypageRepository.valid(member, data, errorList);
        if (errorList.hasError()) {
            throw new RestFulException(new ResponseObject<>(WebConst.ERROR, "에러", errorList));
        }
        mypageRepository.changePassword(member, data);

    }

    public void deleteMember(Member member) {
        member.setMemberStatus(MemberStatusEnum.탈퇴);
        member.setMemberExpireDate(LocalDateTime.now());
    }
}
