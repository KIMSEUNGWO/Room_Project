package project.study.exceptions.login;

import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.RestFulException;

public class FreezeMemberLoginException extends LoginException {


    public FreezeMemberLoginException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }
}
