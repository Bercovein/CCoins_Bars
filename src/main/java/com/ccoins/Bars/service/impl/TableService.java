package com.ccoins.Bars.service.impl;

import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.dto.TableDTO;
import com.ccoins.Bars.exceptions.ObjectNotFoundException;
import com.ccoins.Bars.exceptions.UnauthorizedException;
import com.ccoins.Bars.exceptions.constant.ExceptionConstant;
import com.ccoins.Bars.model.Bar;
import com.ccoins.Bars.model.Table;
import com.ccoins.Bars.repository.IBarsRepository;
import com.ccoins.Bars.repository.ITableRepository;
import com.ccoins.Bars.service.ITableService;
import com.ccoins.Bars.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ccoins.Bars.exceptions.constant.ExceptionConstant.TABLE_FIND_BAR_BY_ID_ERROR_CODE;

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
        Table table;

        try {
            table = (Table) MapperUtils.map(tableDTO, Table.class);
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
    public ResponseEntity<ListDTO> findAllByBar(Long barId) {

        ListDTO response = new ListDTO(new ArrayList<>());

        try {
            Optional<List<Table>> tableOpt = this.repository.findByBar(barId);

            if(tableOpt.isPresent()){
                List<Table> tables = tableOpt.get();
                response.setList(MapperUtils.mapList(tables, TableDTO.class));
            }

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_FIND_BY_OWNER_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.TABLE_FIND_BY_OWNER_ERROR);
        }
    }

    @Override
    public ResponseEntity<TableDTO> findById(Long id) {

        try {
            Optional<Table> table = this.repository.findById(id);
            return ResponseEntity.ok((TableDTO)MapperUtils.map(table,TableDTO.class));
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
}
