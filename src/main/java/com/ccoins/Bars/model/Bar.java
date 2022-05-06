package com.ccoins.Bars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bar {
    private Long id;
    private String name;
    private String address;
    private String menuLink;
    private Long owner;
}
