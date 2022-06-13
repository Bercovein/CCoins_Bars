package com.ccoins.Bars.service;

import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.dto.ResponseDTO;
import com.ccoins.Bars.dto.TableDTO;
import com.ccoins.Bars.dto.TableQuantityDTO;
import com.ccoins.Bars.model.BarTable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ITableService {
    ResponseEntity<TableDTO> saveOrUpdate(TableDTO tableDTO);

    ResponseEntity<ListDTO> findAllByBarAndOptStatus(Long barId, Optional<String> status);

    ResponseEntity<TableDTO> findById(Long id);
    ResponseEntity<TableDTO> active(Long id);

    ResponseEntity<ResponseDTO> createByQuantity(TableQuantityDTO request);

    BarTable generateNewCode(BarTable table);

    void saveAll(List<BarTable> list);

    Long countByBar(Long bar);
}
