package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import project.study.enums.SocialEnum;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "SOCIAL")
@SequenceGenerator(name = "SEQ_SOCIAL", sequenceName = "SEQ_SOCIAL_ID", allocationSize = 1)
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SOCIAL")
    private Long socialId;

    @Enumerated(EnumType.STRING)
    private SocialEnum socialType;

    private String socialEmail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}
