package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROFILE")
@SequenceGenerator(name = "SEQ_PROFILE", sequenceName = "SEQ_PROFILE_ID", allocationSize = 1)
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROFILE")
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private String profileOriginalName;
    private String profileStoreName;
}
