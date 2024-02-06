package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Entity
@Table(name = "FREEZE")
@SequenceGenerator(name = "SEQ_FREEZE", sequenceName = "SEQ_FREEZE_ID", allocationSize = 1)
public class Freeze {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FREEZE")
    private Long freezeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String freezeReason;
    private LocalDateTime freezeStartDate;
    private LocalDateTime freezeEndDate;

}
