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
public class Profile implements ImageFileEntityChildren {

    @Id @GeneratedValue
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String profileOriginalName;
    private String profileStoreName;

    public String getProfileStoreName() {
        return profileStoreName;
    }

    @Override
    public void setImage(String originalName, String storeName) {
        this.profileOriginalName = originalName;
        this.profileStoreName = storeName;


    }
}
