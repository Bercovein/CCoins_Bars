package com.ccoins.Bars.service.impl;

import com.ccoins.Bars.dto.BarDTO;
import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.exceptions.UnauthorizedException;
import com.ccoins.Bars.model.Bar;
import com.ccoins.Bars.repository.IBarsRepository;
import com.ccoins.Bars.service.IBarsService;
import com.ccoins.Bars.exceptions.utils.ErrorUtils;
import com.ccoins.Bars.utils.MapperUtils;
import com.ccoins.Bars.exceptions.constant.ExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BarsService implements IBarsService {

    @Autowired
    private IBarsRepository barsRepository;

    @Override
    public ResponseEntity<BarDTO> saveOrUpdate(BarDTO barDTO) {

        try {
            Bar bar = this.barsRepository.save((Bar)MapperUtils.map(barDTO,Bar.class));
            return ResponseEntity.ok((BarDTO)MapperUtils.map(bar,BarDTO.class));
        }catch(Exception e){
            log.error(ErrorUtils.parseMethodError(this.getClass()));
            throw new UnauthorizedException(ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR_CODE, this.getClass(), ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR);
        }
    }

    @Override
    public ResponseEntity<ListDTO> findAllByOwner(Long ownerId) {

        ListDTO response = new ListDTO(new ArrayList<>());

        try {
            Optional<List<Bar>> barsOpt = this.barsRepository.findByOwner(ownerId);

            if(barsOpt.isPresent()){
                List<Bar> bars = barsOpt.get();
                response.setList((List<BarDTO>)MapperUtils.map(bars, BarDTO.class));
            }

            return ResponseEntity.ok(response);
        }catch(Exception e){
            log.error(ErrorUtils.parseMethodError(this.getClass()));
            throw new UnauthorizedException(ExceptionConstant.BAR_FIND_BY_OWNER_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.BAR_FIND_BY_OWNER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BarDTO> findById(Long id) {

        try {
            Optional<Bar> bar = this.barsRepository.findById(id);
            return ResponseEntity.ok((BarDTO)MapperUtils.map(bar,BarDTO.class));
        }catch(Exception e){
            log.error(ErrorUtils.parseMethodError(this.getClass()));
            throw new UnauthorizedException(ExceptionConstant.BAR_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_FIND_BY_ID_ERROR);
        }
    }
}
