package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.study.enums.MemberStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER")
@SequenceGenerator(name = "SEQ_MEMBER", sequenceName = "SEQ_MEMBER_ID", allocationSize = 1)
public class Member implements ImageFileEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    private Long memberId;
    private String memberName;
    private String memberNickname;
    private LocalDateTime memberCreateDate;
    @Enumerated(EnumType.STRING)
    private MemberStatusEnum memberStatus;
    private int memberNotifyCount;
    private LocalDateTime memberExpireDate;

    // Not Columns
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Social social;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Basic basic;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Profile profile;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Phone phone;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Freeze freeze;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<JoinRoom> joinRoomList;

    public void changeStatusToNormal() {
        memberStatus = MemberStatusEnum.정상;
    }
    public boolean isFreezeMember() {
        return memberStatus.isFreezeMember();
    }

    public boolean isExpireMember() {
        return memberStatus.isExpireMember();
    }

    public boolean isSocialMember() {
        return social != null;
    }

    public boolean isBasicMember() {
        return basic != null;
    }
    public Long getMemberId() {
        return memberId;
    }

    public Social getSocial() {
        return social;
    }

    public Basic getBasic() {
        return basic;
    }

    public List<JoinRoom> getJoinRoomList() {
        return joinRoomList;
    }
}
