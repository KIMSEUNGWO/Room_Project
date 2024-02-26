package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADMIN")
@SequenceGenerator(name = "SEQ_ADMIN", sequenceName = "SEQ_ADMIN_ID", allocationSize = 1)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMIN")
    private Long adminId;

    private String account;
    private String password;
    private String name;
}


