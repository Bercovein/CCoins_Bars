package com.ccoins.Bars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Table {
    private Long id;
    private Long number;
    private boolean active;
    private Bar bar;
}
