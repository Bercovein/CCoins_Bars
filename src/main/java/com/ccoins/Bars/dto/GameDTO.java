package com.ccoins.Bars.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.ccoins.Bars.model.Game;
import com.ccoins.Bars.model.GameType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {

    private Long id;
    private String name;
    private String rules;
    private Long points;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;
    private Long bar;
    private GameTypeDTO gameType;

    public static GameDTO convert(Game game){
        return GameDTO.builder().id(game.getId())
                .name(game.getName())
                .rules(game.getRules())
                .points(game.getPoints())
                .startDate(game.getStartDate())
                .endDate(game.getEndDate())
                .active(game.isActive())
                .bar(game.getBar().getId())
                .gameType(GameTypeDTO.builder()
                    .id(game.getGameType().getId())
                    .name(game.getGameType().getName())
                    .build())
                .build();
    }
}
