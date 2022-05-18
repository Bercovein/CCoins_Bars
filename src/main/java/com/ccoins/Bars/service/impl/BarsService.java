package com.ccoins.Bars.service.impl;

import com.ccoins.Bars.dto.BarDTO;
import com.ccoins.Bars.exceptions.UnauthorizedException;
import com.ccoins.Bars.model.Bar;
import com.ccoins.Bars.repository.IBarsRepository;
import com.ccoins.Bars.service.IBarsService;
import com.ccoins.Bars.utils.ErrorUtils;
import com.ccoins.Bars.utils.MapperUtils;
import com.ccoins.Bars.utils.constant.ExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
