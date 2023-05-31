package com.ccoins.bars.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StateEnum {

    ACTIVE("active", true),
    UNACTIVE("unactive", false);

    final String value;
    final boolean status;

}
