package com.ccoins.bars.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

import static com.ccoins.bars.utils.DateUtils.HH_MM;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Game")
@Table(name = "GAMES")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="rules")
    private String rules;

    @Column(name="points")
    private Long points;

    @Column(name="open_time")
    @JsonFormat(pattern = HH_MM)
    private LocalTime openTime;

    @Column(name="close_time")
    @JsonFormat(pattern = HH_MM)
    private LocalTime closeTime;

    @Column(name="active", columnDefinition = "boolean default true")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_bar", referencedColumnName = "id")
    private Bar bar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_game_type", referencedColumnName = "id")
    private GameType gameType;


}
