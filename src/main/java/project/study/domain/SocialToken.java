package project.study.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SOCIAL_TOKEN")
@SequenceGenerator(name = "SEQ_SOCIAL_TOKEN", sequenceName = "SEQ_SOCIAL_TOKEN_ID", allocationSize = 1)
@NoArgsConstructor
public class SocialToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SOCIAL_TOKEN")
    private Long socialTokenId;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOCIAL_ID")
    private Social social;

    @Getter @Setter
    private String access_token;
    @Getter @Setter
    private String refresh_token;

    public SocialToken(String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

}
