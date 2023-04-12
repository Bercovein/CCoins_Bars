package com.ccoins.bars.dto;

import com.ccoins.bars.model.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayDTO {

    private Long id;

    @NotEmpty
    private String name;

    public static DayDTO convert(Day day) {
        return DayDTO.builder()
                .id(day.getId())
                .name(day.getName())
                .build();
    }
}
