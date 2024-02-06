package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.study.enums.WeekEnum;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "SEQ_ROOM_SCHEDULE", sequenceName = "SEQ_ROOM_SCHEDULE_ID", allocationSize = 1)
public class RoomSchedule {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM_SCHEDULE")
    @Column(name = "room_schedule_id")
    private Long roomScheduleId;

    @Enumerated(EnumType.STRING)
    private WeekEnum week;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

}
