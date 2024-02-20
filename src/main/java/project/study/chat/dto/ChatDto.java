package project.study.chat.dto;

import lombok.*;
import project.study.chat.MessageType;

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
    private Long token;
    private String sender;
    private String senderImage;
    private String message;
    private LocalDateTime time;


}
