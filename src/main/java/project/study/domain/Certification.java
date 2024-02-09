package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Entity
@Table(name = "CERTIFICATION")
@SequenceGenerator(name = "SEQ_CERTIFICATION", sequenceName = "SEQ_CERTIFICATION_ID", allocationSize = 1)
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CERTIFICATION")
    private Long certificationId;

    private String name;
    private String phone;
    private String certificationNumber;

    private LocalDateTime expireDate;
}
