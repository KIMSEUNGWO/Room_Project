package project.study.authority.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.study.enums.NotifyStatus;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class SearchNotifyDto {

    private String criminalMemberAccount;
    private String reporterMemberAccount;
    private String notifyReason;
    private Long roomId;
    private String notifyDate;
    private Long notifyId;
    private String notifyContent;
    private NotifyStatus notifyStatus;

    @QueryProjection
    public SearchNotifyDto(String criminalMemberAccount, String reporterMemberAccount, String notifyReason, Long roomId, String notifyDate, Long notifyId, String notifyContent, NotifyStatus notifyStatus) {
        this.criminalMemberAccount = criminalMemberAccount;
        this.reporterMemberAccount = reporterMemberAccount;
        this.notifyReason = notifyReason;
        this.roomId = roomId;
        this.notifyDate = notifyDate;
        this.notifyId = notifyId;
        this.notifyContent = notifyContent;
        this.notifyStatus = notifyStatus;
    }
}
