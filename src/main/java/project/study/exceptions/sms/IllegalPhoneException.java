package project.study.exceptions.sms;

import project.study.dto.abstractentity.ResponseDto;

public class IllegalPhoneException extends SmsException{
    public IllegalPhoneException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }

    public IllegalPhoneException(ResponseDto responseDto) {
        super(responseDto);
    }
}
