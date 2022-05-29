package com.ccoins.Bars.controller;

import com.ccoins.Bars.dto.BarDTO;
import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.service.IBarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bars")
public class BarsController {

    private final IBarsService service;

    @Autowired
    public BarsController(IBarsService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<BarDTO> saveOrUpdate(@RequestBody BarDTO barDTO){
        return this.service.saveOrUpdate(barDTO);
    }

    @GetMapping("/owner/{ownerId}")
    ResponseEntity<ListDTO> findAllByOwner(@PathVariable("ownerId") Long ownerId) {
        return this.service.findAllByOwner(ownerId);
    }

    @GetMapping("/{id}")
    ResponseEntity<BarDTO> findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @GetMapping("/{id}/active")
    ResponseEntity<BarDTO> active(@PathVariable("id") Long id){
        return this.service.active(id);
    }
}
