package com.n75jr.nexign.lottery.domain;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "winner_records")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class WinnerRecord {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum")
    private Integer sum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#%6d: ", id))
                .append(String.format("[%s]", sum))
                .append(String.format("[%s]", createdAt));
        return sb.toString();
    }
}
