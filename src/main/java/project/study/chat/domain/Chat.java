package project.study.chat.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import project.study.domain.Room;

import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "CHAT")
@SequenceGenerator(name = "SEQ_CHAT", sequenceName = "SEQ_CHAT_ID", allocationSize = 1)
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHAT")
    private Long chatId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<ChatHistory> chatHistory;
}
