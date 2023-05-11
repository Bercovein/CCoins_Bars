package com.ccoins.bars.service;

import com.ccoins.bars.dto.GameDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.model.Bar;
import com.ccoins.bars.model.Game;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IGamesService {
    ResponseEntity<GameDTO> saveOrUpdate(GameDTO barDTO);

    ResponseEntity<ListDTO> findAllByBar(Long id);

    ResponseEntity<GameDTO> findById(Long id);

    Optional<Game> findGameBarById(Long id);

    ResponseEntity<GameDTO> active(Long id);

    ResponseEntity<ListDTO> findAllTypes();

    ResponseEntity<GameDTO> findGameByBarIdAndGame(Long barId, String game);

    ResponseEntity<ListDTO> findAllActiveByBar(Long id);

    void addVoteGameToBarIfDoNotHave(Bar bar);
    void addCodeGameToBarIfDoNotHave(Bar bar);

}
