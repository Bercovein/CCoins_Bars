package com.ccoins.bars.dto;

import com.ccoins.bars.model.Game;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static com.ccoins.bars.utils.DateUtils.HH_MM;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {

    private Long id;
    private String name;
    private String rules;
    private Long points;

    @JsonFormat(pattern = HH_MM)
    private LocalTime openTime;

    @JsonFormat(pattern = HH_MM)
    private LocalTime closeTime;
    private boolean active;
    private Long bar;
    private GameTypeDTO gameType;

    public static GameDTO convert(Game game){
        return GameDTO.builder().id(game.getId())
                .name(game.getName())
                .rules(game.getRules())
                .points(game.getPoints())
                .active(game.isActive())
                .bar(game.getBar().getId())
                .gameType(GameTypeDTO.builder()
                    .id(game.getGameType().getId())
                    .name(game.getGameType().getName())
                    .build())
                .build();
    }
}
