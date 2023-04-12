package com.ccoins.bars.model.projection;

import java.time.LocalTime;

public interface IPBarHour {

    Long getId();
    Long getBarId();
    IPDay getDay();
    LocalTime getOpenTime();
    LocalTime getCloseTime();
}
