package com.ccoins.bars.model.projection;

public interface IPBarTable {

    Long getId();
    Long getNumber();
    boolean getActive();
    String getQrCode();

    Long setId();
    Long setNumber();
    boolean setActive();
    String setQrCode();
}
