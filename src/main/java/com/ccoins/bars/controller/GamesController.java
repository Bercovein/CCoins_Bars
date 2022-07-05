package com.ccoins.bars.controller;

import com.ccoins.bars.dto.GameDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.service.IGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final IGamesService service;

    @Autowired
    public GamesController(IGamesService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<GameDTO> saveOrUpdate(@RequestBody GameDTO barDTO){
        return this.service.saveOrUpdate(barDTO);
    }

    @GetMapping("/bar/{id}")
    ResponseEntity<ListDTO> findAllByBar(@PathVariable("id") Long id) {
        return this.service.findAllByBar(id);
    }

    @GetMapping("/{id}")
    ResponseEntity<GameDTO> findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}/active")
    ResponseEntity<GameDTO> active(@PathVariable("id") Long id){
        return this.service.active(id);
    }

    @GetMapping("/types")
    ResponseEntity<ListDTO> findAllTypes() {
        return this.service.findAllTypes();
    }
}
