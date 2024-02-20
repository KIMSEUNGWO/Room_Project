package project.study.dto.abstractentity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseObject {

    private String result;
    private Object object;
}
