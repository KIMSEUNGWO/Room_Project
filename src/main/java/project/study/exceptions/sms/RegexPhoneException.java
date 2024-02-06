package project.study.exceptions.sms;

import project.study.dto.abstractentity.ResponseDto;

public class RegexPhoneException extends IllegalPhoneException{
    public RegexPhoneException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }

    public RegexPhoneException(ResponseDto responseDto) {
        super(responseDto);
    }
}
