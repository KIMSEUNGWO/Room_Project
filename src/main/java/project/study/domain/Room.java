package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.study.enums.PublicEnum;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "SEQ_ROOM", sequenceName = "SEQ_ROOM_ID", allocationSize = 1)
public class Room implements ImageFileEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROOM")
    @Column(name = "room_id")
    private Long roomId;

    private String roomTitle;

    private String roomIntro;

    private int roomLimit;

    @Enumerated(EnumType.STRING)
    private PublicEnum roomPublic;

    @OneToMany(mappedBy = "room")
    private List<Tag> tags;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private RoomImage roomImage;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private RoomPassword roomPassword;

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private RoomNotice roomNotice;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<JoinRoom> joinRoomList;

    public boolean isPublic() {
        return roomPublic.isPublic();
    }

}
