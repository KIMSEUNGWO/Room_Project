package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "SEQ_ROOM_DELETE", sequenceName = "SEQ_ROOM_DELETE_ID", allocationSize = 1)
public class RoomDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM_DELETE")
    private Long roomDeleteId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    private LocalDateTime roomDeleteDate;
}
