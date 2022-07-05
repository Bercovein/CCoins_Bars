package com.ccoins.bars.dto;

import com.ccoins.bars.model.BarTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.ccoins.bars.utils.DateUtils.DDMMYYYY_HHMM;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarTableDTO {

    private Long id;

    private Long number;

    private boolean active;

    private Long bar;

    private String code;

    @JsonFormat(pattern = DDMMYYYY_HHMM)
    private LocalDateTime startDate;

    public static BarTableDTO convert(BarTable tbl){
        return BarTableDTO.builder().id(tbl.getId())
                .active(tbl.isActive())
                .bar(tbl.getBar().getId())
                .number(tbl.getNumber())
                .code(tbl.getQrCode())
                .startDate(tbl.getStartDate())
                .build();
    }
}
