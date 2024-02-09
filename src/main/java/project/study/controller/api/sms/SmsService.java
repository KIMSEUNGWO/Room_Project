package project.study.controller.api.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.study.domain.Certification;
import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.sms.IllegalPhoneException;
import project.study.exceptions.sms.NotFoundCertificationNumberException;
import project.study.jpaRepository.CertificationJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static project.study.constant.WebConst.ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    // 인증번호는 현재시간 기준 5분 후 만료
    private static final int EXPIRE_TIME = 5;


    private final SmsRepository smsRepository;
    private final CertificationJpaRepository certificationJpaRepository;

    protected String sendSMS(RequestSms data) {
        String certificationNumber = smsRepository.createCertificationNumber();

        Message message = smsRepository.createMessage(data, certificationNumber);

        smsRepository.sendSms(message);

        return certificationNumber;
    }

    protected void regexPhone(String phone) {
        if (phone == null || phone.length() < 10) {
            throw new IllegalPhoneException();
        }
    }


    @Transactional
    public void saveCertification(RequestSms data) {
        Certification saveCertification = Certification.builder()
                .name(data.getName())
                .phone(data.getPhone())
                .certificationNumber(data.getCertification())
                .expireDate(LocalDateTime.now().plusMinutes(EXPIRE_TIME))
                .build();

        certificationJpaRepository.save(saveCertification);
    }

    public Certification findCertification(String certification) {
        Optional<Certification> findCertification = certificationJpaRepository.findTopByCertificationNumberOrderByCertificationId(certification);
        if (findCertification.isEmpty()) {
            throw new NotFoundCertificationNumberException(new ResponseDto(ERROR, "인증번호가 틀렸습니다."));
        }
        return findCertification.get();
    }
}
