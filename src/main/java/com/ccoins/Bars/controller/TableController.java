package com.ccoins.Bars.controller;

import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.dto.ResponseDTO;
import com.ccoins.Bars.dto.TableDTO;
import com.ccoins.Bars.dto.TableQuantityDTO;
import com.ccoins.Bars.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tables")
public class TableController {

    private final ITableService service;

    @Autowired
    public TableController(ITableService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<TableDTO> saveOrUpdate(@RequestBody TableDTO tableDTO){
        return this.service.saveOrUpdate(tableDTO);
    }

    @PostMapping("/quantity")
    public ResponseEntity<ResponseDTO> createByQuantity(@RequestBody TableQuantityDTO request){
        return this.service.createByQuantity(request);
    }

    @GetMapping("/bar/{barId}")
    ResponseEntity<ListDTO> findAllByBar(@PathVariable("barId") Long barId) {
        return this.service.findAllByBar(barId);
    }

    @GetMapping("/{id}")
    ResponseEntity<TableDTO> findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}/active")
    ResponseEntity<TableDTO> active(@PathVariable("id") Long id){
        return this.service.active(id);
    }
}
