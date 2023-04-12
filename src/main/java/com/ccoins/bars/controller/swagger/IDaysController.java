package com.ccoins.bars.controller.swagger;

import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.LongListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "DAYS")
public interface IDaysController {
    @ApiOperation(value = "Get all days from week")
    ResponseEntity<ListDTO> getDays();

    @ApiOperation(value = "Get all hours from bar")
    ResponseEntity<ListDTO> getBarHours(@PathVariable("barId") Long barId);

    @ApiOperation(value = "Save or update bar hour")
    void saveOrUpdate(@RequestBody ListDTO request);

    @ApiOperation(value = "Delete hours from id list")
    void delete(@RequestBody LongListDTO request);
}
