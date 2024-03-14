package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "FREEZE")
@SequenceGenerator(name = "SEQ_FREEZE", sequenceName = "SEQ_FREEZE_ID", allocationSize = 1)
public class Freeze {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FREEZE")
    private Long freezeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    private String freezeReason;
    private LocalDateTime freezeEndDate;


    public boolean isFinish() {
        return LocalDateTime.now().isAfter(freezeEndDate);
    }

    public String printMessage() {
        return combineMessage(freezeEndDate, freezeReason);
    }

    private String combineMessage(LocalDateTime endDate, String reason) {
        String dateFormat = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return String.format("이용이 정지된 회원입니다. \n ~ %s 까지 \n 사유 : %s", dateFormat ,reason);
    }


}
