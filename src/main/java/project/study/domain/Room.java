package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "SEQ_ROOM", sequenceName = "SEQ_ROOM_ID", allocationSize = 1)
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM")
    @Column(name = "room_id")
    private Long roomId;

    private String roomTitle;

    private String roomIntro;

    private int roomLimit;

    @OneToMany(mappedBy = "room")
    private List<Tag> tags;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private RoomImage roomImage;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private RoomPassword roomPassword;

    @OneToMany(mappedBy = "room")
    private List<RoomSchedule> roomSchedules;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private RoomNotice roomNotice;
}
