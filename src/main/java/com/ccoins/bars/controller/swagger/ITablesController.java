package com.ccoins.bars.controller.swagger;

import com.ccoins.bars.dto.*;
import com.ccoins.bars.model.projection.IPBarTable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Api(tags = "TABLES")
public interface ITablesController {

    @ApiOperation(value = "Save or update bar table")
    ResponseEntity<BarTableDTO> saveOrUpdate(@RequestBody BarTableDTO tableDTO);

    @ApiOperation(value = "Create bar tables by quantity")
    ResponseEntity<ResponseDTO> createByQuantity(@RequestBody TableQuantityDTO request);

    @ApiOperation(value = "Delete bar tables by quantity")
    ResponseEntity<ResponseDTO> deleteByQuantity(@RequestBody TableQuantityDTO request);

    @ApiOperation(value = "Returns a list of bar tables by bar id and status")
    ResponseEntity<ListDTO> findAllByBarAndOptStatus(
            @PathVariable("barId") Long barId,
            @PathVariable("status") Optional<String> status);

    @ApiOperation(value = "Returns a bar table by id")
    ResponseEntity<BarTableDTO> findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Active/Unactive bar table by id")
    ResponseEntity<BarTableDTO> active(@PathVariable("id") Long id);

    @ApiOperation(value = "Active/Unactive by list of bar table ids")
    ResponseEntity<ResponseDTO> activeByList(@RequestBody LongListDTO request);

    @ApiOperation(value = "Generate QR Codes by list of bar tables id")
    ResponseEntity<ResponseDTO> generateCodesByList(@RequestBody LongListDTO request);

    @ApiOperation(value = "Returns bar table list by id list")
    List<BarTableDTO> findByIdIn(@RequestBody LongListDTO request);

    @ApiOperation(value = "Returns a bar table projection by QR code")
    IPBarTable findByCode(@PathVariable("code") String code);

    @ApiOperation(value = "Returns activity of bar and table by QR code")
    ResponseEntity<GenericRsDTO<?>> isActiveByQrCode(String qrCode);

}
