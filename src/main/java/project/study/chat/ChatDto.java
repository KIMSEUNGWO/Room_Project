package project.study.chat;

import lombok.*;
import project.study.domain.Member;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatDto {

    private MessageType type;
    private Long roomId;
    private String sender;
    private String message;
    private LocalDateTime time;


}
