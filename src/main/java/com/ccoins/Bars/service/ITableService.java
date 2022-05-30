package com.ccoins.Bars.service;

import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.dto.TableDTO;
import org.springframework.http.ResponseEntity;

public interface ITableService {
    ResponseEntity<TableDTO> saveOrUpdate(TableDTO tableDTO);

    ResponseEntity<ListDTO> findAllByBar(Long barId);

    ResponseEntity<TableDTO> findById(Long id);
    ResponseEntity<TableDTO> active(Long id);
}
