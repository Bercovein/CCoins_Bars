package com.ccoins.bars.service.impl;

import com.ccoins.bars.dto.BarDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.dto.StringDTO;
import com.ccoins.bars.exceptions.UnauthorizedException;
import com.ccoins.bars.exceptions.constant.ExceptionConstant;
import com.ccoins.bars.model.Bar;
import com.ccoins.bars.model.projection.IPBar;
import com.ccoins.bars.repository.IBarsRepository;
import com.ccoins.bars.repository.ITableRepository;
import com.ccoins.bars.service.IBarsService;
import com.ccoins.bars.utils.MapperUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BarsService implements IBarsService {

    private final IBarsRepository repository;
    private final ITableRepository tableRepository;

    @Autowired
    public BarsService(IBarsRepository repository, ITableRepository tableRepository) {
        this.repository = repository;
        this.tableRepository = tableRepository;
    }

    @Override
    public ResponseEntity<BarDTO> saveOrUpdate(BarDTO barDTO) {

        try {
            Bar bar = this.repository.save(this.convert(barDTO));
            return ResponseEntity.ok(this.convert(bar));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR);
        }
    }

    @Override
    public ResponseEntity<ListDTO> findAllByOwner(Long ownerId) {

        ListDTO response = new ListDTO(new ArrayList<>());

        try {
            Optional<List<IPBar>> barsOpt = this.repository.findByOwner(ownerId);

            if(barsOpt.isPresent()){
                response.setList(barsOpt.get());
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
            return ResponseEntity.ok(this.convert(bar.get()));
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
            throw new UnauthorizedException(ExceptionConstant.BAR_UPDATE_ACTIVE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_UPDATE_ACTIVE_ERROR);
        }
        return this.findById(id);
    }

    @Override
    public ResponseEntity<StringDTO> findUrlByTableCode(String code) {

        try{
            StringDTO response = StringDTO.builder().text(
                    this.tableRepository.findMenuByQrCode(code).orElse(Strings.EMPTY)
            ).build();
            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_GET_MENU_BY_CODE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_GET_MENU_BY_CODE_ERROR);
        }
    }

    private Bar convert(BarDTO barDTO){
        return (Bar)MapperUtils.map(barDTO,Bar.class);
    }

    private BarDTO convert(Bar bar){
        return (BarDTO)MapperUtils.map(bar,BarDTO.class);
    }
}
