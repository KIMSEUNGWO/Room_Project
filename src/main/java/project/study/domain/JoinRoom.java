package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.study.enums.AuthorityMemberEnum;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "JOIN_ROOM")
@SequenceGenerator(name = "SEQ_JOIN_ROOM", sequenceName = "SEQ_JOIN_ROOM_ID", allocationSize = 1)
public class JoinRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JOIN_ROOM")
    private Long joinRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @Enumerated(EnumType.STRING)
    private AuthorityMemberEnum authorityEnum;

    public AuthorityMemberEnum getAuthority() {
        return authorityEnum;
    }

    public Room getRoom() {
        return room;
    }

    public Long getJoinRoomId() {
        return joinRoomId;
    }
}
