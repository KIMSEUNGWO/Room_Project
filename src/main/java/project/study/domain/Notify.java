package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import project.study.enums.NotifyStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "NOTIFY")
@SequenceGenerator(name = "SEQ_NOTIFY", sequenceName = "SEQ_NOTIFY_ID", allocationSize = 1)
public class Notify implements ImageFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTIFY")
    private Long notifyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_memberId")  // reporter 필드에 대한 컬럼명 변경
    private Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criminal_memberId")  // criminal 필드에 대한 컬럼명 변경
    private Member criminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    private String notifyReason;
    private String notifyContent;
    private LocalDateTime notifyDate;

    @Enumerated(EnumType.STRING)
    private NotifyStatus notifyStatus;

    // Not Columns
    @OneToOne(mappedBy = "notify", fetch = FetchType.LAZY)
    private NotifyImage notifyImage;

}
