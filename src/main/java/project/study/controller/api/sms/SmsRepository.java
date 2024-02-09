package project.study.controller.api.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.sms.MessageSendException;

import java.util.Random;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SmsRepository {

    @Value("${sms.key}")
    private String apiKey;
    @Value("${sms.secret}")
    private String secretKey;
    @Value("${sms.phone}")
    private String fromPhone;
    private final String url = "https://api.coolsms.co.kr";

    protected Message createMessage(RequestSms data, String certificationNumber) {
        Message message = new Message();
        message.setFrom(fromPhone);
        message.setTo(data.getPhone());
        message.setText(String.format("[모각코] 본인확인 \n 인증번호는 [%s] 입니다.", certificationNumber));
        return message;
    }


    protected void sendSms(Message message) {

        try {
            DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, url);
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            log.error("{}", e.getFailedMessageList());
            throw new MessageSendException(new ResponseDto("NotConnected", "메시지 전송에 실패했습니다."));
        } catch (NurigoEmptyResponseException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException(new ResponseDto("NotConnected", "메세지 서버의 응답이 없습니다."));
        } catch (NurigoUnknownException e) {
            log.error("[SmsService] [메세지 전송에러] {}", e.getMessage());
            throw new MessageSendException(new ResponseDto("NotConnected", "메세지 서버 응답없음. 관리자에게 문의해주세요"));
        }
    }

    protected String createCertificationNumber() {
        return new Random()
                .ints(0, 9)
                .limit(5)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}
