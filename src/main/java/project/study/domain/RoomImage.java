package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ROOM_IMAGE")
public class RoomImage implements ImageFileEntityChildren {

    @Id @GeneratedValue
    @Column(name = "ROOM_IMAGE_ID")
    private Long roomImageId;

    private String roomImageOriginalName;

    private String roomImageStoreName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    public String getRoomImageStoreName() {
        return roomImageStoreName;
    }

    @Override
    public void setImage(String originalName, String storeName) {
        this.roomImageOriginalName = originalName;
        this.roomImageStoreName = storeName;

    }
}
