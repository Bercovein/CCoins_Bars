package com.ccoins.bars.service;

import com.ccoins.bars.dto.GameDTO;
import com.ccoins.bars.dto.ListDTO;
import org.springframework.http.ResponseEntity;

public interface IGamesService {
    ResponseEntity<GameDTO> saveOrUpdate(GameDTO barDTO);

    ResponseEntity<ListDTO> findAllByBar(Long id);

    ResponseEntity<GameDTO> findById(Long id);

    ResponseEntity<GameDTO> active(Long id);

    ResponseEntity<ListDTO> findAllTypes();
}
