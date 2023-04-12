package com.ccoins.bars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "BarHour")
@Table(name = "BAR_HOURS")
public class BarHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DAY", referencedColumnName = "id")
    private Day day;

    @Column(name = "FK_BAR")
    private Long barId;

    @Column(name="OPEN_TIME")
    private LocalTime openTime;

    @Column(name="CLOSE_TIME")
    private LocalTime closeTime;

}
