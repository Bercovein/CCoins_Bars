package com.ccoins.bars.controller.swagger;

import com.ccoins.bars.dto.BarDTO;
import com.ccoins.bars.dto.IdDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.StringDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "BAR")
public interface IBarsController {
    @ApiOperation(value = "Save or update bar")
    ResponseEntity<BarDTO> saveOrUpdate(@RequestBody BarDTO barDTO);

    @ApiOperation(value = "Find all bars by owner")
    ResponseEntity<ListDTO> findAllByOwner(@PathVariable("ownerId") Long ownerId);

    @ApiOperation(value = "Find bar by id")
    ResponseEntity<BarDTO> findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Active/Unactive by id")
    ResponseEntity<BarDTO> active(@PathVariable("id") Long id);

    @ApiOperation(value = "Get Url by Table code")
    ResponseEntity<StringDTO> findUrlByTableCode(@PathVariable("code") String code);

    @ApiOperation(value = "Get Bar id by Party Id")
    ResponseEntity<IdDTO> getBarIdByParty(@PathVariable("partyId") Long partyId);

    @ApiOperation(value = "Get bar by Game Id")
    ResponseEntity<BarDTO> getBarByGame(@RequestParam("id") Long id);
}
