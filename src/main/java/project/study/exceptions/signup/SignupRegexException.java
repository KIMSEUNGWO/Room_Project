package project.study.exceptions.signup;

import project.study.dto.abstractentity.ResponseDto;

public class SignupRegexException extends SignupException{
    public SignupRegexException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }

    public SignupRegexException(ResponseDto responseDto) {
        super(responseDto);
    }
}
