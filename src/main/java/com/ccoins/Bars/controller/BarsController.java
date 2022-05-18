package com.ccoins.Bars.controller;

import com.ccoins.Bars.dto.BarDTO;
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
}
