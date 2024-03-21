package project.study.authority.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.study.enums.NotifyStatus;
import project.study.enums.NotifyType;

@Getter
@ToString
@NoArgsConstructor
@Builder
public class SearchNotifyDto {

    private String reporterMemberAccount;
    private String criminalMemberAccount;
    private Long roomId;
    private String notifyDate;
    private NotifyType notifyReason;
    private Long notifyId;
    private NotifyStatus notifyStatus;

    @QueryProjection
    public SearchNotifyDto(String reporterMemberAccount, String criminalMemberAccount, Long roomId, String notifyDate, NotifyType notifyReason, Long notifyId, NotifyStatus notifyStatus) {
        this.reporterMemberAccount = reporterMemberAccount;
        this.criminalMemberAccount = criminalMemberAccount;
        this.roomId = roomId;
        this.notifyDate = notifyDate;
        this.notifyReason = notifyReason;
        this.notifyId = notifyId;
        this.notifyStatus = notifyStatus;
    }
}
