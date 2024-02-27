package project.study.authority.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.annotation.security.DenyAll;
import lombok.*;
import project.study.domain.NotifyImage;
import project.study.enums.NotifyStatus;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@Builder
public class SearchNotifyDto {

    private String reporterMemberAccount;
    private String criminalMemberAccount;
    private Long roomId;
    private String notifyDate;
    private String notifyReason;
    private Long notifyId;
    private NotifyStatus notifyStatus;

    @QueryProjection
    public SearchNotifyDto(String reporterMemberAccount, String criminalMemberAccount, Long roomId, String notifyDate, String notifyReason, Long notifyId, NotifyStatus notifyStatus) {
        this.reporterMemberAccount = reporterMemberAccount;
        this.criminalMemberAccount = criminalMemberAccount;
        this.roomId = roomId;
        this.notifyDate = notifyDate;
        this.notifyReason = notifyReason;
        this.notifyId = notifyId;
        this.notifyStatus = notifyStatus;
    }
}
