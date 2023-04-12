package com.ccoins.bars.service;

import com.ccoins.bars.dto.BarHourDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.LongListDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDaysService {

    ResponseEntity<ListDTO> getDays();

    ResponseEntity<ListDTO> getHoursByBar(Long barId);

    List<BarHourDTO> saveOrUpdate(ListDTO request);

    void delete(LongListDTO request);
}
