package com.ccoins.Bars.service.impl;

import com.ccoins.Bars.dto.*;
import com.ccoins.Bars.exceptions.BadRequestException;
import com.ccoins.Bars.exceptions.ObjectNotFoundException;
import com.ccoins.Bars.exceptions.UnauthorizedException;
import com.ccoins.Bars.exceptions.constant.ExceptionConstant;
import com.ccoins.Bars.model.Bar;
import com.ccoins.Bars.model.BarTable;
import com.ccoins.Bars.model.projection.IPBarTable;
import com.ccoins.Bars.repository.IBarsRepository;
import com.ccoins.Bars.repository.ITableRepository;
import com.ccoins.Bars.service.ITableService;
import com.ccoins.Bars.utils.EncodeUtils;
import com.ccoins.Bars.utils.MapperUtils;
import com.ccoins.Bars.utils.StateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ccoins.Bars.exceptions.constant.ExceptionConstant.TABLE_FIND_BAR_BY_ID_ERROR_CODE;
import static com.ccoins.Bars.utils.ResponseMessages.*;

@Service
@Slf4j
public class TableService implements ITableService {

    private final ITableRepository repository;

    private final IBarsRepository barRepository;


    @Autowired
    public TableService(ITableRepository repository, IBarsRepository barRepository) {
        this.repository = repository;
        this.barRepository = barRepository;
    }

    @Override
    public ResponseEntity<TableDTO> saveOrUpdate(TableDTO tableDTO) {

        Optional<Bar> barOpt;
        BarTable table;

        try {
            table = (BarTable) MapperUtils.map(tableDTO, BarTable.class);
            barOpt = this.barRepository.findById(tableDTO.getBar());

            if(barOpt.isEmpty()){
                throw new ObjectNotFoundException(TABLE_FIND_BAR_BY_ID_ERROR_CODE, this.getClass(),
                        ExceptionConstant.TABLE_FIND_BAR_BY_ID_ERROR);
            }

            table.setBar(barOpt.get());
            table = this.repository.save(table);
            return ResponseEntity.ok((TableDTO)MapperUtils.map(table,TableDTO.class));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR_CODE, this.getClass(),
                    ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR);
        }
    }

    @Override
    public ResponseEntity<ListDTO> findAllByBarAndOptStatus(Long barId, Optional<String> status) {

        ListDTO response = new ListDTO(new ArrayList<>());
        Optional<List<IPBarTable>> tableOpt;
        try {

            if(status.isPresent()){

                boolean state = StateUtils.isActive(status.get());

                tableOpt = this.repository.findByBarIdAndActive(barId, state);
            }else{
                tableOpt = this.repository.findByBarId(barId);
            }

            tableOpt.ifPresent(response::setList);

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_FIND_BY_OWNER_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.TABLE_FIND_BY_OWNER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TableDTO> findById(Long id) {

        TableDTO response = null;

        try {
            Optional<BarTable> table = this.repository.findById(id);

            if(table.isPresent()){
                BarTable tbl = table.get();
                response = TableDTO.builder().id(tbl.getId())
                        .active(tbl.isActive())
                        .bar(tbl.getBar().getId())
                        .number(tbl.getNumber())
                        .build();
            }

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public ResponseEntity<TableDTO> active(Long id) {
        try {
            this.repository.updateActive(id);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_UPDATE_ACTIVE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_UPDATE_ACTIVE_ERROR);
        }
        return this.findById(id);
    }

    @Override
    public ResponseEntity<ResponseDTO> createByQuantity(TableQuantityDTO request) {

        Bar bar;
        List<BarTable> list = new ArrayList<>();
        Long quantity = request.getQuantity();
        Long actual;

        bar = this.getBarById(request.getBar());

        actual = this.countByBar(bar.getId());

        for (long i = actual + 1; i < actual + quantity + 1; i++) {

            BarTable table = BarTable.builder().bar(bar).number(i).active(true).build();
            table = this.generateNewCode(table);

            list.add(table);
        }

        this.saveAll(list);

        return ResponseEntity.ok(new GenericRsDTO<>(SUCCESS_CODE,String.format(TABLES_CREATED_BY_QUANTITY,quantity),null));
    }

    @Override
    public BarTable generateNewCode(BarTable table){
        table.setQrCode(EncodeUtils.generateCode());
        return table;
    }

    @Override
    public void saveAll(List<BarTable> list){
        try {
            this.repository.saveAll(list);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_CREATE_LIST_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_CREATE_LIST_ERROR);
        }
    }

    @Override
    public Long countByBar(Long bar){
        try {
            return this.repository.countByBarId(bar);
        }catch(Exception e){
            throw new BadRequestException(ExceptionConstant.TABLE_COUNT_BY_BAR_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_COUNT_BY_BAR_ERROR);
        }
    }


    private Bar getBarById(Long request){
        try {
            return this.barRepository.getById(request);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> deleteByQuantity(TableQuantityDTO request) {

        Optional<List<BarTable>> listOpt;
        Long quantity = request.getQuantity();
        List<BarTable> list;

        try {

            listOpt = this.repository.findByBarIdOrderByIdDescLimit(request.getBar(), request.getQuantity());

            if (listOpt.isPresent()) {
                list = listOpt.get();

                this.repository.deleteAllInBatch(list);
            } else {
                return ResponseEntity.ok(new GenericRsDTO<>(SUCCESS_CODE, String.format(TABLES_DELETED_BY_QUANTITY, 0), null));
            }

            return ResponseEntity.ok(new GenericRsDTO<>(SUCCESS_CODE, String.format(TABLES_DELETED_BY_QUANTITY, quantity), null));

        }catch(Exception e){
            throw new BadRequestException(ExceptionConstant.TABLE_DELETE_BY_QUANTITY_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_DELETE_BY_QUANTITY_ERROR);
        }
    }
}
