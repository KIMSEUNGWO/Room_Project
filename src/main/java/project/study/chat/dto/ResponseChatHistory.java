package project.study.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseChatHistory {

    private Long memberId;
    private String image;
    private String message;
    private LocalDateTime date;


}
