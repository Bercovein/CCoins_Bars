package com.ccoins.Bars.service;

import com.ccoins.Bars.dto.BarDTO;
import org.springframework.http.ResponseEntity;

public interface IBarsService {
    ResponseEntity<BarDTO> saveOrUpdate(BarDTO barDTO);

}
