package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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

    @Getter
    @Builder
    public static class ResponseRoomNotice {

        private String content; // 공지사항 내용
        private LocalDateTime time; // 공지사항 시간

    }

}
