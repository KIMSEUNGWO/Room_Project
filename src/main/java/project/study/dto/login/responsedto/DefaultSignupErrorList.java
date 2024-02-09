package project.study.dto.login.responsedto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DefaultSignupErrorList {

    private final List<DefaultSignupError> errorList;

    public DefaultSignupErrorList() {
        this.errorList = new ArrayList<>();
    }

    public void addError(DefaultSignupError error) {
        errorList.add(error);
    }

    public boolean hasError() {
        return !errorList.isEmpty();
    }
}
