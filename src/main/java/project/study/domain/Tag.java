package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name = "SEQ_TAG", sequenceName = "SEQ_TAG_ID", allocationSize = 1)
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TAG")
    @Column(name = "tag_id")
    private Long tagId;

    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
