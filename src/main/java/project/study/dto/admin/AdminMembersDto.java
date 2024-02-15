package project.study.dto.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import project.study.domain.Phone;
import project.study.domain.Social;
import project.study.enums.MemberStatusEnum;
import project.study.enums.SocialEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class AdminMembersDto {

    private String account;
    private String socialEmail;
    private String memberName;
    private String memberNickname;
    private String phone;
    private String memberCreateDate;
    private int memberNotifyCount;
    private SocialEnum socialType;
    private MemberStatusEnum memberStatusEnum;

    @QueryProjection
    public AdminMembersDto(String account, String socialEmail, String memberName, String memberNickname, String phone, String memberCreateDate, int memberNotifyCount, SocialEnum socialType, MemberStatusEnum memberStatusEnum) {
        this.account = account;
        this.socialEmail = socialEmail;
        this.memberName = memberName;
        this.memberNickname = memberNickname;
        this.phone = phone;
        this.memberCreateDate = memberCreateDate;
        this.memberNotifyCount = memberNotifyCount;
        this.socialType = socialType;
        this.memberStatusEnum = memberStatusEnum;
    }
}
