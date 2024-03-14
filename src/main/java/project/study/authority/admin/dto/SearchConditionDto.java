package project.study.authority.admin.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@ToString
@AllArgsConstructor
public class SearchConditionDto {

    @Nullable
    private String word;
    @Nullable
    private String freezeOnly;
    @Nullable
    private int pageNumber;



}
