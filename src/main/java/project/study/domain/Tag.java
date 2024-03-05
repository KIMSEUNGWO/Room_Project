package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TAG")
public class Tag {

    @Id @GeneratedValue
    @Column(name = "TAG_ID")
    private Long tagId;

    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

}
