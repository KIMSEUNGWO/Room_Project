package project.study.chat.domain;

import jakarta.persistence.*;
import lombok.*;
import project.study.domain.Member;
import project.study.domain.Room;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "CHAT")
@SequenceGenerator(name = "SEQ_CHAT", sequenceName = "SEQ_CHAT_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHAT")
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member sendMember;

    @Lob
    private String message;
    private LocalDateTime sendDate;

    @Getter
    public static class ResponseChatHistory {

        private Long token;
        private String sender;
        private String senderImage;
        private String message;
        private LocalDateTime time;

        public ResponseChatHistory(Member sendMember, String message, LocalDateTime sendDate) {
            this.token = sendMember.getMemberId();
            this.sender = sendMember.getMemberNickname();
            this.senderImage = sendMember.getStoreImage();
            this.message = message;
            this.time = sendDate;
        }

    }
    public ResponseChatHistory getResponseChatHistory() {
        return new ResponseChatHistory(sendMember, message, sendDate);
    }
}
