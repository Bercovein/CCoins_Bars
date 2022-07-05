package com.ccoins.Bars.exceptions.dto;

import com.ccoins.Bars.dto.ResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
public class ExceptionRsDTO extends ResponseDTO {

    @Builder
    public ExceptionRsDTO(String code, Object message) {
        super(code, message);
    }

    public ExceptionRsDTO() {
        super();
    }
}
