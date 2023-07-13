package com.ccoins.bars.controller;

import com.ccoins.bars.controller.swagger.ITablesController;
import com.ccoins.bars.dto.*;
import com.ccoins.bars.service.ITableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tables")
@CrossOrigin
public class TableController implements ITablesController{

    private final ITableService service;

    @Autowired
    public TableController(ITableService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<BarTableDTO> saveOrUpdate(@RequestBody BarTableDTO tableDTO){
        return this.service.saveOrUpdate(tableDTO);
    }

    @Override
    @PostMapping("/quantity")
    public ResponseEntity<ResponseDTO> createByQuantity(@RequestBody TableQuantityDTO request){
        return this.service.createByQuantity(request);
    }

    @Override
    @DeleteMapping("/quantity")
    public ResponseEntity<ResponseDTO> deleteByQuantity(@RequestBody TableQuantityDTO request){
        return this.service.deleteByQuantity(request);
    }

    @Override
    @GetMapping({"/bar/{barId}", "/bar/{barId}/{status}"})
    public ResponseEntity<ListDTO> findAllByBarAndOptStatus(
            @PathVariable("barId") Long barId,
            @PathVariable("status") Optional<String> status)
    {
        return this.service.findAllByBarAndOptStatus(barId, status);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BarTableDTO> findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @Override
    @PutMapping("/{id}/active")
    public ResponseEntity<BarTableDTO> active(@PathVariable("id") Long id){
        return this.service.active(id);
    }

    @Override
    @PutMapping
    public ResponseEntity<ResponseDTO> activeByList(@RequestBody LongListDTO request){
        return this.service.activeByList(request);
    }

    @Override
    @PutMapping("/codes")
    public ResponseEntity<ResponseDTO> generateCodesByList(@RequestBody LongListDTO request){
        return this.service.generateCodesByList(request);
    }

    @Override
    @PostMapping("/list")
    public List<BarTableDTO> findByIdIn(@RequestBody LongListDTO request){
        return this.service.findByIdIn(request.getList());
    }

    @Override
    @GetMapping("/code/{code}")
    public BarTableDTO findByCode(@PathVariable("code") String code){
        return this.service.findByCode(code);
    }

    @Override
    @GetMapping("/code/{code}/active")
    public ResponseEntity<GenericRsDTO<?>> isActiveByQrCode(@PathVariable("code") String code){
        return this.service.isActiveByQrCode(code);
    }
}
