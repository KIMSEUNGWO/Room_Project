package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.study.enums.MemberStatusEnum;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MEMBER")
@SequenceGenerator(name = "SEQ_MEMBER", sequenceName = "SEQ_MEMBER_ID", allocationSize = 1)
public class Member {

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
    private Profile profile;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Phone phone;
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Freeze freeze;

    public void changeStatusToNormal() {
        memberStatus = MemberStatusEnum.정상;
    }
    public boolean isFreezeMember() {
        return memberStatus.isFreezeMember();
    }

    public boolean isExpireMember() {
        return memberStatus.isExpireMember();
    }

    public Long getMemberId() {
        return memberId;
    }
}
