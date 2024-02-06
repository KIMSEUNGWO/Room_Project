package project.study.exceptions.roomcreate;

import project.study.dto.abstractentity.ResponseDto;

public class InvalidPasswordException extends CreateRoomException{
    public InvalidPasswordException(Throwable cause, ResponseDto responseDto) {
        super(cause, responseDto);
    }
}
