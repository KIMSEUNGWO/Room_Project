package project.study.authority.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.study.enums.PublicEnum;

@Getter
@ToString
@NoArgsConstructor
public class AdminRoomsDto {

    private Long roomId;
    private String roomTitle;
//    private int joinMemberCount;
    private int roomLimit;
    private String managerMember;
    private String roomCreateDate;
    private PublicEnum roomPublic;

    @QueryProjection
    public AdminRoomsDto(Long roomId, String roomTitle, int joinMemberCount, int roomLimit, String managerMember, String roomCreateDate, PublicEnum roomPublic) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
//        this.joinMemberCount = joinMemberCount;
        this.roomLimit = roomLimit;
        this.managerMember = managerMember;
        this.roomCreateDate = roomCreateDate;
        this.roomPublic = roomPublic;
    }
}
