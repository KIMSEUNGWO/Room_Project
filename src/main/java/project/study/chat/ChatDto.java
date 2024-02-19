package project.study.chat;

import lombok.*;

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
    private String token;
    private String sender;
    private String senderImage;
    private String message;
    private LocalDateTime time;


}
