package project.study.exceptions.login;

import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.RestFulException;

public class InvalidLoginException extends LoginException {

    public InvalidLoginException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }
}

