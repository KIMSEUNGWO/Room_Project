package project.study.dto.login.responsedto;

import project.study.dto.abstractentity.ResponseDto;

public class ResponseSignupdto extends ResponseDto {

    private DefaultSignupErrorList errorList;

    public ResponseSignupdto(String result, String message, DefaultSignupErrorList errorList) {
        super(result, message);
        this.errorList = errorList;
    }
}
