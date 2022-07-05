package com.ccoins.bars.model.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import static com.ccoins.bars.utils.DateUtils.DDMMYYYY_HHMM;

public interface IPBar {

    Long getId();
    String getName();
    String getAddress();
    String getMenuLink();
    boolean isActive();
    Long getOwner();

    @JsonFormat(pattern = DDMMYYYY_HHMM)
    LocalDateTime getStartDate();
    String getLocation();
}
