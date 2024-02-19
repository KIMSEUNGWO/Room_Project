package project.study.chat.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import project.study.domain.Member;
import project.study.domain.Room;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "CHAT")
@SequenceGenerator(name = "SEQ_CHAT_HISTORY", sequenceName = "SEQ_CHAT_HISTORY_ID", allocationSize = 1)
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHAT_HISTORY")
    private Long chatHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member sendMember;
    private String message;
    private LocalDateTime sendDate;
}
