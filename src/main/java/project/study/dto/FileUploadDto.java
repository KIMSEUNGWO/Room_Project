package project.study.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileUploadDto {

    private Long imageId;
    private Long roomId;
    private String originalName;
    private String storeName;
}
