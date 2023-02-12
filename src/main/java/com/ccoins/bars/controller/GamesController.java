package com.ccoins.bars.controller;

import com.ccoins.bars.controller.swagger.IGamesController;
import com.ccoins.bars.dto.GameDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.service.IGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GamesController implements IGamesController {

    private final IGamesService service;

    @Autowired
    public GamesController(IGamesService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<GameDTO> saveOrUpdate(@RequestBody GameDTO gameDTO){
        return this.service.saveOrUpdate(gameDTO);
    }

    @Override
    @GetMapping("/bar/{id}")
    public ResponseEntity<ListDTO> findAllByBar(@PathVariable("id") Long id) {
        return this.service.findAllByBar(id);
    }

    @Override
    @GetMapping("/bar/{id}/active")
    public ResponseEntity<ListDTO> findAllActiveByBar(@PathVariable("id") Long id) {
        return this.service.findAllActiveByBar(id);
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @Override
    @PutMapping("/{id}/active")
    public ResponseEntity<GameDTO> active(@PathVariable("id") Long id){
        return this.service.active(id);
    }

    @Override
    @GetMapping("/types")
    public ResponseEntity<ListDTO> findAllTypes() {
        return this.service.findAllTypes();
    }

    @GetMapping("/voting/bar/{id}")
    public ResponseEntity<GameDTO> findVotingGameByBarId(@PathVariable("id") Long id){
        return this.service.findVotingGameByBarId(id);
    }
}
