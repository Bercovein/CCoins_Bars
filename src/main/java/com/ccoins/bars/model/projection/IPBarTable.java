package com.ccoins.bars.model.projection;

public interface IPBarTable {

    Long getId();
    Long getNumber();
    String getQrCode();
    boolean getActive();

    Long setId();
    Long setNumber();
    String setQrCode();
    boolean setActive();
}
