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
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String profileOriginalName;
    private String profileStoreName;

    public String getProfileStoreName() {
        return profileStoreName;
    }

    public void setProfileOriginalName(String profileOriginalName) {
        this.profileOriginalName = profileOriginalName;
    }

    public void setProfileStoreName(String profileStoreName) {
        this.profileStoreName = profileStoreName;
    }
}
