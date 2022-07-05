package com.ccoins.Bars.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import static com.ccoins.Bars.utils.DateUtils.DDMMYYYY_HHMM;

public interface IPGame {
    
    Long getId();
    String getName();
    String getRules();
    Long getPoints();

    @JsonFormat(pattern = DDMMYYYY_HHMM)
    LocalDateTime getStartDate();

    @JsonFormat(pattern = DDMMYYYY_HHMM)
    LocalDateTime getEndDate();
    boolean getActive();
    IPGameType getGameType();
}
