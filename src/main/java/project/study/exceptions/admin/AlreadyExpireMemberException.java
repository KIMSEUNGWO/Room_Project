package project.study.exceptions.admin;

import project.study.dto.abstractentity.ResponseDto;

public class AlreadyExpireMemberException extends AdminException{

    public AlreadyExpireMemberException(ResponseDto responseDto) {
        super(responseDto);
    }
}
