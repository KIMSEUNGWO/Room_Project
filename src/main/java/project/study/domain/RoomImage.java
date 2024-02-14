package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "SEQ_ROOM_IMAGE", sequenceName = "SEQ_ROOM_IMAGE_ID", allocationSize = 1)
public class RoomImage {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM_IMAGE")
    @Column(name = "room_image_id")
    private Long roomImageId;

    private String roomImageOriginalName;

    private String roomImageStoreName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public String getRoomImageStoreName() {
        return roomImageStoreName;
    }
}
