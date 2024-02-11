package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BASIC")
@SequenceGenerator(name = "SEQ_BASIC", sequenceName = "SEQ_BASIC_ID", allocationSize = 1)
public class Basic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BASIC")
    private Long basicId;

    private String account;
    private String password;
    private String salt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public Member getMember() {
        return member;
    }

    public void changePassword(String changePassword) {
        password = changePassword;
    }
}
