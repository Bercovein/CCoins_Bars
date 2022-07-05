package com.ccoins.Bars.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;

import static com.ccoins.Bars.utils.DateUtils.HH_MM;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    private String menuLink;

    private boolean active;

    private Long owner;

    @JsonFormat(pattern = HH_MM)
    private LocalTime openTime;

    @JsonFormat(pattern = HH_MM)
    private LocalTime closeTime;

    private String location;
}
