package com.ccoins.bars.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

import static com.ccoins.bars.utils.DateUtils.HH_MM;

public interface IPGame {
    
    Long getId();
    String getName();
    String getRules();
    Long getPoints();

    @JsonFormat(pattern = HH_MM)
    LocalTime getOpenTime();

    @JsonFormat(pattern = HH_MM)
    LocalTime getCloseTime();
    boolean getActive();
    IPGameType getGameType();
}
