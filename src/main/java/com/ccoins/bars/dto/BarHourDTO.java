package com.ccoins.bars.dto;

import com.ccoins.bars.model.BarHour;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static com.ccoins.bars.utils.DateUtils.HH_MM;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarHourDTO {

    private Long id;

    private Long barId;

    private DayDTO day;

    @JsonFormat(pattern = HH_MM)
    private LocalTime openTime;

    @JsonFormat(pattern = HH_MM)
    private LocalTime closeTime;

    public static BarHourDTO convert (BarHour request){
        return BarHourDTO.builder()
                .id(request.getId())
                .barId(request.getBarId())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .day(DayDTO.convert(request.getDay()))
                .build();
    }
}
