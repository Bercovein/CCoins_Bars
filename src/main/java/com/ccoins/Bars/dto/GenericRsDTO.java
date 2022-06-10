package com.ccoins.Bars.dto;

import lombok.Data;

@Data
public class GenericRsDTO<T> extends ResponseDTO {

    private T data;

    public GenericRsDTO(String code, Object message, T data) {
        super(code, message);
        this.data = data;
    }

    public GenericRsDTO() {
        super();
    }


}
