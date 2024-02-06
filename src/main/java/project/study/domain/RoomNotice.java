package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "SEQ_ROOM_NOTICE", sequenceName = "SEQ_ROOM_NOTICE_ID", allocationSize = 1)
public class RoomNotice {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM_NOTICE")
    @Column(name = "room_notice_id")
    private Long roomNoticeId;

    private String roomNoticeContent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room")
    private Room room;


}
