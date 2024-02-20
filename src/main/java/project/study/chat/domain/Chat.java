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
@SequenceGenerator(name = "SEQ_CHAT", sequenceName = "SEQ_CHAT_ID", allocationSize = 1)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHAT")
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member sendMember;

    @Lob
    private String message;
    private LocalDateTime sendDate;
}
