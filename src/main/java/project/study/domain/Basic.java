package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "SOCIAL")
@SequenceGenerator(name = "SEQ_SOCIAL", sequenceName = "SEQ_SOCIAL_ID", allocationSize = 1)
public class Basic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SOCIAL")
    private Long basicId;

    private String account;
    private String password;
    private String salt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

}
