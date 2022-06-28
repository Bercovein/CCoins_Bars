package com.ccoins.Bars.service;

import com.ccoins.Bars.dto.*;
import com.ccoins.Bars.model.BarTable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ITableService {
    ResponseEntity<BarTableDTO> saveOrUpdate(BarTableDTO barTableDTO);

    ResponseEntity<ListDTO> findAllByBarAndOptStatus(Long barId, Optional<String> status);

    ResponseEntity<BarTableDTO> findById(Long id);
    ResponseEntity<BarTableDTO> active(Long id);

    ResponseEntity<ResponseDTO> activeByList(LongListDTO request);

    ResponseEntity<ResponseDTO> findByIdIn(List<Long> list);

    ResponseEntity<ResponseDTO> createByQuantity(TableQuantityDTO request);

    BarTable generateNewCode(BarTable table);

    List<BarTable> saveAll(List<BarTable> list);

    Long countByBar(Long bar);

    ResponseEntity<ResponseDTO> deleteByQuantity(TableQuantityDTO request);

    ResponseEntity<ResponseDTO> generateCodesByList(LongListDTO idList);
}
