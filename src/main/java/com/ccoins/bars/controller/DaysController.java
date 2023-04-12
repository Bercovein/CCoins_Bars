package com.ccoins.bars.controller;

import com.ccoins.bars.controller.swagger.IDaysController;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.LongListDTO;
import com.ccoins.bars.service.IDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/days")
public class DaysController implements IDaysController {

    private final IDaysService service;

    @Autowired
    public DaysController(IDaysService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<ListDTO> getDays(){
        return this.service.getDays();
    }

    @GetMapping("/bar/{barId}")
    public ResponseEntity<ListDTO> getBarHours(@PathVariable("barId") Long barId){
        return this.service.getHoursByBar(barId);
    }

    @PostMapping("/bar")
    public void saveOrUpdate(@RequestBody ListDTO request){
        this.service.saveOrUpdate(request);
    }

    @DeleteMapping("/bar")
    public void delete(@RequestBody LongListDTO request){
        this.service.delete(request);
    }
}
