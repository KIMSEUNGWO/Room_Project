package project.study.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "KAKAO_TOKEN")
@SequenceGenerator(name = "SEQ_KAKAO_TOKEN", sequenceName = "SEQ_KAKAO_TOKEN_ID", allocationSize = 1)
@NoArgsConstructor
public class KakaoToken{

    public KakaoToken(String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KAKAO_TOKEN")
    private Long kakaoTokenId;

    @OneToOne
    @JoinColumn(name = "socialId")
    private Social social;

    private String access_token;
    private String refresh_token;

    public String getAccess_token() {
        return access_token;
    }
}
