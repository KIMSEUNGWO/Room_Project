package project.study.authority.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.study.domain.Member;
import project.study.enums.BanEnum;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestNotifyMemberFreezeDto {

    private Long memberId;
    private BanEnum freezePeriod;
    private String freezeReason;

}
