package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String freezeReason;
    private LocalDateTime freezeEndDate;


    public boolean isFinish() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(freezeEndDate);
    }

    public LocalDateTime getFreezeEndDate() {
        return freezeEndDate;
    }

    public String getFreezeReason() {
        return freezeReason;
    }
}
