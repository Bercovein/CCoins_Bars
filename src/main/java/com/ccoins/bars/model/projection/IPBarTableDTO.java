package com.ccoins.bars.model.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IPBarTableDTO {

    private Long id;
    private Long number;
    private boolean active;
    private String qrCode;
}
