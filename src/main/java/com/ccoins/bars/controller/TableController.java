package com.ccoins.bars.controller;

import com.ccoins.bars.dto.*;
import com.ccoins.bars.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tables")
public class TableController {

    private final ITableService service;

    @Autowired
    public TableController(ITableService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<BarTableDTO> saveOrUpdate(@RequestBody BarTableDTO tableDTO){
        return this.service.saveOrUpdate(tableDTO);
    }

    @PostMapping("/quantity")
    public ResponseEntity<ResponseDTO> createByQuantity(@RequestBody TableQuantityDTO request){
        return this.service.createByQuantity(request);
    }

    @DeleteMapping("/quantity")
    public ResponseEntity<ResponseDTO> deleteByQuantity(@RequestBody TableQuantityDTO request){
        return this.service.deleteByQuantity(request);
    }

    @GetMapping({"/bar/{barId}", "/bar/{barId}/{status}"})
    ResponseEntity<ListDTO> findAllByBarAndOptStatus(
            @PathVariable("barId") Long barId,
            @PathVariable("status") Optional<String> status)
    {
        return this.service.findAllByBarAndOptStatus(barId, status);
    }

    @GetMapping("/{id}")
    ResponseEntity<BarTableDTO> findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @PutMapping("/{id}/active")
    ResponseEntity<BarTableDTO> active(@PathVariable("id") Long id){
        return this.service.active(id);
    }

    @PutMapping
    ResponseEntity<ResponseDTO> activeByList(@RequestBody LongListDTO request){
        return this.service.activeByList(request);
    }

    @PutMapping("/codes")
    ResponseEntity<ResponseDTO> generateCodesByList(@RequestBody LongListDTO request){
        return this.service.generateCodesByList(request);
    }

    @PostMapping("/list")
    List<BarTableDTO> findByIdIn(@RequestBody LongListDTO request){
        return this.service.findByIdIn(request.getList());
    }

}
