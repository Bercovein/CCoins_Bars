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

    @Autowired
    IBarsService barsService;

    @PostMapping
    ResponseEntity<BarDTO> saveOrUpdate(@RequestBody BarDTO barDTO){
        return this.barsService.saveOrUpdate(barDTO);
    }

    @GetMapping("/owner/{ownerId}")
    ResponseEntity<ListDTO> findAllByOwner(@PathVariable("ownerId") Long ownerId) {
        return this.barsService.findAllByOwner(ownerId);
    }

    @GetMapping("/{id}")
    ResponseEntity<BarDTO> findById(@PathVariable("id") Long id) {
        return this.barsService.findById(id);
    }
}
