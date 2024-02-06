package project.study.exceptions.sms;

import project.study.dto.abstractentity.ResponseDto;

public class DistinctPhoneException extends IllegalPhoneException{
    public DistinctPhoneException(ResponseDto responseDto) {
        super(responseDto);
    }

    public DistinctPhoneException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }
}
