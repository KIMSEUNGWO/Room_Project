package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.study.chat.domain.Chat;
import project.study.enums.PublicEnum;

import java.time.LocalDateTime;
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

    private LocalDateTime roomCreateDate;

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

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private RoomDelete roomDelete;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<JoinRoom> joinRoomList;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Chat> chatHistory;

    public boolean isPublic() {
        return roomPublic.isPublic();
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public void setRoomIntro(String roomIntro) {
        this.roomIntro = roomIntro;
    }

    public void setRoomLimit(int roomLimit) {
        this.roomLimit = roomLimit;
    }

    public void setRoomPublic(PublicEnum roomPublic) {
        this.roomPublic = roomPublic;
    }

    public boolean hasNotice() {
        return roomNotice != null;
    }

    public boolean isDeleteRoom() {
        return roomDelete != null;
    }
}
