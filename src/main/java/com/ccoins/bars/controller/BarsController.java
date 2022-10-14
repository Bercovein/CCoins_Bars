package com.ccoins.bars.controller;

import com.ccoins.bars.controller.swagger.IBarsController;
import com.ccoins.bars.dto.BarDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.StringDTO;
import com.ccoins.bars.service.IBarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bars")
public class BarsController implements IBarsController {

    private final IBarsService service;

    @Autowired
    public BarsController(IBarsService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<BarDTO> saveOrUpdate(@RequestBody BarDTO barDTO){
        return this.service.saveOrUpdate(barDTO);
    }

    @Override
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ListDTO> findAllByOwner(@PathVariable("ownerId") Long ownerId) {
        return this.service.findAllByOwner(ownerId);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BarDTO> findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @Override
    @PutMapping("/{id}/active")
    public ResponseEntity<BarDTO> active(@PathVariable("id") Long id){
        return this.service.active(id);
    }

    @GetMapping("/menu/table/{code}")
    @Override
    public ResponseEntity<StringDTO> findUrlByTableCode(@PathVariable("code") String code){
        return this.service.findUrlByTableCode(code);
    }
}
