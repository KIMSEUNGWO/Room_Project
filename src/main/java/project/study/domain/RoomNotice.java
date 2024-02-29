package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ROOM_NOTICE")
@SequenceGenerator(name = "SEQ_ROOM_NOTICE", sequenceName = "SEQ_ROOM_NOTICE_ID", allocationSize = 1)
public class RoomNotice {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM_NOTICE")
    @Column(name = "ROOM_NOTICE_ID")
    private Long roomNoticeId;

    private String roomNoticeContent;
    private LocalDateTime roomNoticeDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM")
    private Room room;


    public String getRoomNoticeContent() {
        return roomNoticeContent;
    }

    public LocalDateTime getRoomNoticeDate() {
        return roomNoticeDate;
    }

    public void updateNotice(String notice) {
        this.roomNoticeContent = notice;
        this.roomNoticeDate = LocalDateTime.now();
    }
}
