package com.ccoins.Bars.service;

import com.ccoins.Bars.dto.BarDTO;
import com.ccoins.Bars.dto.ListDTO;
import org.springframework.http.ResponseEntity;

public interface IBarsService {
    ResponseEntity<BarDTO> saveOrUpdate(BarDTO barDTO);

    ResponseEntity<ListDTO> findAllByOwner(Long ownerId);

    ResponseEntity<BarDTO> findById(Long id);

    ResponseEntity<BarDTO> active(Long id);
}
