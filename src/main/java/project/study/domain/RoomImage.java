package project.study.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "ROOM_IMAGE")
@SequenceGenerator(name = "SEQ_ROOM_IMAGE", sequenceName = "SEQ_ROOM_IMAGE_ID", allocationSize = 1)
public class RoomImage extends ImageFileEntityChildren {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM_IMAGE")
    @Column(name = "ROOM_IMAGE_ID")
    private Long roomImageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public RoomImage(Room room, String originalName, String storeName) {
        super(originalName, storeName);
        this.room = room;
    }
}
