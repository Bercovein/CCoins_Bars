package com.ccoins.bars.model.projection;

import java.time.LocalDateTime;

public interface IPBar {

    Long getId();
    String getName();
    String getAddress();
    String getMenuLink();
    boolean isActive();
    Long getOwner();

    LocalDateTime getStartDate();
    String getLocation();
}
