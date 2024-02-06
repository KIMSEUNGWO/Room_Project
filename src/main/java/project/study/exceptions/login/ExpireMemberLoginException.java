package project.study.exceptions.login;

import project.study.dto.abstractentity.ResponseDto;

public class ExpireMemberLoginException extends LoginException{

    public ExpireMemberLoginException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }

    public ExpireMemberLoginException(ResponseDto responseDto) {
        super(responseDto);
    }
}
