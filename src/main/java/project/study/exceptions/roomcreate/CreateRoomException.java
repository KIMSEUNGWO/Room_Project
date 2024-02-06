package project.study.exceptions.roomcreate;

import project.study.dto.abstractentity.ResponseDto;
import project.study.exceptions.RestFulException;

public class CreateRoomException extends RestFulException {
    public CreateRoomException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }
}
