package project.study.authority.admin.dto;

import jakarta.validation.constraints.Null;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchConditionDto {

    private String word;
    private String freezeOnly;
    private String containComplete;
    private int page = 1;
}
