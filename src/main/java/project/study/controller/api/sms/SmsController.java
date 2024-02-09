package project.study.controller.api.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.study.domain.Certification;
import project.study.dto.abstractentity.ResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
@Slf4j
public class SmsController {




    private final SmsService smsService;

    @PostMapping("/account/find")
    public ResponseEntity<ResponseDto> accountSendSMS(@RequestBody RequestSms data) {
        System.out.println("/account/find data = " + data);

        smsService.regexPhone(data.getPhone());

        String certification = smsService.sendSMS(data);

        data.setCertification(certification);
        smsService.saveCertification(data);

        return new ResponseEntity<>(new ResponseDto("ok", "인증번호를 발송했습니다."), HttpStatus.OK);
    }

    @PostMapping("/account/confirm")
    public ResponseEntity<ResponseDto> accountConfirm(@RequestBody RequestSms data) {

        Certification certification = smsService.findCertification(data.getCertification());



        return new ResponseEntity<>(new ResponseDto("ok", "인증번호를 발송했습니다."), HttpStatus.OK);
    }

}
