package com.ccoins.bars.exceptions.dto;

import com.ccoins.bars.dto.ResponseDTO;
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
