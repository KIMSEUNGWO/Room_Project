package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADMIN")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "SEQ_ADMIN", sequenceName = "SEQ_ADMIN_ID", allocationSize = 1)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMIN")
    private Long adminId;

    private String account;
    private String password;
}
