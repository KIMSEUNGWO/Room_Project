package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.study.dto.room.ResponseRoomNotice;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ROOM_NOTICE")
public class RoomNotice {

    @Id @GeneratedValue
    @Column(name = "ROOM_NOTICE_ID")
    private Long roomNoticeId;

    private String roomNoticeContent;
    private LocalDateTime roomNoticeDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM")
    private Room room;

    public void updateNotice(String notice) {
        this.roomNoticeContent = notice;
        this.roomNoticeDate = LocalDateTime.now();
    }

    public ResponseRoomNotice buildResponseRoomNotice() {
        return ResponseRoomNotice
            .builder()
            .content(roomNoticeContent)
            .time(roomNoticeDate)
            .build();
    }
}
