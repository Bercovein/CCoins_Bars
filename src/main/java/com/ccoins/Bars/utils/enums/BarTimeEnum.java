package com.ccoins.bars.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BarTimeEnum {

    ON_TIME("Exitoso"),
    OUT_TIME("Se encuentra fuera del horario establecido"),
    NO_TABLE("La mesa no se encuentra activa");

    final String message;
}
