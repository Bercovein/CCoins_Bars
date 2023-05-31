package com.ccoins.bars.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameEnum {

    VOTE("VOTE"),
    GAME("GAME"),
    CODE("CODE");

    final String value;
}
