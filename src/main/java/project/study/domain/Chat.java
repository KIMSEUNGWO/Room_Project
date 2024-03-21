package project.study.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.study.constant.WebConst;

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
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "MEMBER_ID")
    private Member sendMember;

    @Lob
    private String message;
    private LocalDateTime sendDate;

    @Getter
    public static class ResponseChatHistory {

        private final boolean isMe;
        private final String sender;
        private final String senderImage;
        private final String message;
        private final LocalDateTime time;

        public ResponseChatHistory(Member sendMember, String message, LocalDateTime sendDate, Long memberId) {
            if (sendMember == null) {
                this.isMe = false;
                this.sender = "탈퇴한 사용자";
                this.senderImage = WebConst.EXPIRE_MEMBER_PROFILE;
            } else {
                this.isMe = sendMember.getMemberId().equals(memberId);
                this.sender = sendMember.getMemberNickname();
                this.senderImage = sendMember.getStoreImage();
            }
            this.message = message;
            this.time = sendDate;
        }

    }
    public ResponseChatHistory getResponseChatHistory(Long memberId) {
        return new ResponseChatHistory(sendMember, message, sendDate, memberId);
    }
}
