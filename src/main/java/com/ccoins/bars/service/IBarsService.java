package com.ccoins.bars.service;

import com.ccoins.bars.dto.BarDTO;
import com.ccoins.bars.dto.IdDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.StringDTO;
import org.springframework.http.ResponseEntity;

public interface IBarsService {
    ResponseEntity<BarDTO> saveOrUpdate(BarDTO barDTO);

    ResponseEntity<ListDTO> findAllByOwner(Long ownerId);

    ResponseEntity<BarDTO> findById(Long id);

    ResponseEntity<BarDTO> active(Long id);

    ResponseEntity<StringDTO> findUrlByTableCode(String code);

    ResponseEntity<IdDTO> getBarIdByParty(Long id);
}
