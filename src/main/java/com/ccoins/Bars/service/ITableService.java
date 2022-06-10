package com.ccoins.Bars.service;

import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.dto.ResponseDTO;
import com.ccoins.Bars.dto.TableDTO;
import com.ccoins.Bars.dto.TableQuantityDTO;
import com.ccoins.Bars.model.Table;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITableService {
    ResponseEntity<TableDTO> saveOrUpdate(TableDTO tableDTO);

    ResponseEntity<ListDTO> findAllByBar(Long barId);

    ResponseEntity<TableDTO> findById(Long id);
    ResponseEntity<TableDTO> active(Long id);

    ResponseEntity<ResponseDTO> createByQuantity(TableQuantityDTO request);

    Table generateNewCode(Table table);

    void saveAll(List<Table> list);

    Long countByBar(Long bar);
}
