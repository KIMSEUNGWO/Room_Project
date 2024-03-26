package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BAN")
@SequenceGenerator(name = "SEQ_BAN", sequenceName = "SEQ_BAN_ID", allocationSize = 1)
public class Ban {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BAN")
    private Long banId;
    private Long memberId;
    private String banAccount;
    private String banName;
    private String banNickname;
    private String banPhone;
    private LocalDateTime suspendedDate;

}
