package project.study.exceptions.admin;

import project.study.dto.abstractentity.ResponseDto;

public class AlreadyExpireRoomException extends AdminException{

    public AlreadyExpireRoomException(ResponseDto responseDto) {
        super(responseDto);
    }
}
