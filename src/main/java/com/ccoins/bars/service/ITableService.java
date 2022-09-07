package com.ccoins.bars.service;

import com.ccoins.bars.dto.*;
import com.ccoins.bars.model.BarTable;
import com.ccoins.bars.model.projection.IPBarTable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ITableService {
    ResponseEntity<BarTableDTO> saveOrUpdate(BarTableDTO barTableDTO);

    ResponseEntity<ListDTO> findAllByBarAndOptStatus(Long barId, Optional<String> status);

    ResponseEntity<BarTableDTO> findById(Long id);
    ResponseEntity<BarTableDTO> active(Long id);

    ResponseEntity<ResponseDTO> activeByList(LongListDTO request);

    List<BarTableDTO> findByIdIn(List<Long> list);

    Optional<List<BarTable>> findIn(List<Long> list);

    ResponseEntity<ResponseDTO> createByQuantity(TableQuantityDTO request);

    BarTable generateNewCode(BarTable table);

    List<BarTable> saveAll(List<BarTable> list);

    Long countByBar(Long bar);

    ResponseEntity<ResponseDTO> deleteByQuantity(TableQuantityDTO request);

    ResponseEntity<ResponseDTO> generateCodesByList(LongListDTO idList);

    IPBarTable findByCode(String code);

    ResponseEntity<GenericRsDTO<?>> isActiveByQrCode(String qrCode);
}
