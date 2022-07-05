package com.ccoins.Bars.model.projection;

import java.time.LocalDateTime;

public interface IPGame {
    
    Long getId();
    String getName();
    String getRules();
    Long getPoints();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    boolean getActive();
    IPGameType getGameType();
}
