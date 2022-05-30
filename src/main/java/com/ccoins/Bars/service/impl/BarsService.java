package com.ccoins.Bars.service.impl;

import com.ccoins.Bars.dto.BarDTO;
import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.exceptions.UnauthorizedException;
import com.ccoins.Bars.exceptions.constant.ExceptionConstant;
import com.ccoins.Bars.model.Bar;
import com.ccoins.Bars.repository.IBarsRepository;
import com.ccoins.Bars.service.IBarsService;
import com.ccoins.Bars.utils.MapperUtils;
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

    private final IBarsRepository repository;

    @Autowired
    public BarsService(IBarsRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<BarDTO> saveOrUpdate(BarDTO barDTO) {

        try {
            Bar bar = this.repository.save((Bar)MapperUtils.map(barDTO,Bar.class));
            return ResponseEntity.ok((BarDTO)MapperUtils.map(bar,BarDTO.class));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR_CODE, this.getClass(), ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR);
        }
    }

    @Override
    public ResponseEntity<ListDTO> findAllByOwner(Long ownerId) {

        ListDTO response = new ListDTO(new ArrayList<>());

        try {
            Optional<List<Bar>> barsOpt = this.repository.findByOwner(ownerId);

            if(barsOpt.isPresent()){
                List<Bar> bars = barsOpt.get();
                response.setList(MapperUtils.mapList(bars, BarDTO.class));
            }

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_FIND_BY_OWNER_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.BAR_FIND_BY_OWNER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BarDTO> findById(Long id) {

        try {
            Optional<Bar> bar = this.repository.findById(id);
            return ResponseEntity.ok((BarDTO)MapperUtils.map(bar,BarDTO.class));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public ResponseEntity<BarDTO> active(Long id) {

        try {
            this.repository.updateActive(id);
        }catch(Exception e){
            e.printStackTrace();
            throw new UnauthorizedException(ExceptionConstant.TABLE_UPDATE_ACTIVE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_UPDATE_ACTIVE_ERROR);
        }
        return this.findById(id);
    }
}
