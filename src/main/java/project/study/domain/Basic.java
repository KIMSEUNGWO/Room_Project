package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.study.controller.api.sms.FindAccount;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BASIC")
public class Basic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long basicId;

    private String account;
    private String password;
    private String salt;

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

}
