package project.study.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "NOTIFY_IMAGE")
public class NotifyImage {

    @Id
    @GeneratedValue
    private Long notifyImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTIFY_ID")
    private Notify notify;

    private String notifyImageOriginalName;
    private String notifyImageStoreName;
}
