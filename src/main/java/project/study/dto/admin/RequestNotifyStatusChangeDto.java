package project.study.dto.admin;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestNotifyStatusChangeDto {

    private Long notifyId;
    private String status;
}
