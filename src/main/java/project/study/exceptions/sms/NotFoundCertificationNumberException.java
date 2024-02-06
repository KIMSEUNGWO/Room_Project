package project.study.exceptions.sms;

import project.study.dto.abstractentity.ResponseDto;

public class NotFoundCertificationNumberException extends SmsException {
    public NotFoundCertificationNumberException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }
}
