package com.ccoins.bars.service.impl;

import com.ccoins.bars.dto.*;
import com.ccoins.bars.exceptions.BadRequestException;
import com.ccoins.bars.exceptions.ObjectNotFoundException;
import com.ccoins.bars.exceptions.UnauthorizedException;
import com.ccoins.bars.exceptions.constant.ExceptionConstant;
import com.ccoins.bars.model.Bar;
import com.ccoins.bars.model.BarTable;
import com.ccoins.bars.model.projection.IPBarTable;
import com.ccoins.bars.model.projection.IPBarTableDTO;
import com.ccoins.bars.repository.IBarsRepository;
import com.ccoins.bars.repository.ITableRepository;
import com.ccoins.bars.service.ITableService;
import com.ccoins.bars.utils.DateUtils;
import com.ccoins.bars.utils.EncodeUtils;
import com.ccoins.bars.utils.MapperUtils;
import com.ccoins.bars.utils.StateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ccoins.bars.exceptions.constant.ExceptionConstant.TABLE_FIND_BAR_BY_ID_ERROR_CODE;
import static com.ccoins.bars.utils.ResponseMessages.*;

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
    public ResponseEntity<BarTableDTO> saveOrUpdate(BarTableDTO barTableDTO) {

        Optional<Bar> barOpt;
        BarTable table;

        try {
            table = (BarTable) MapperUtils.map(barTableDTO, BarTable.class);
            barOpt = this.barRepository.findById(barTableDTO.getBar());

            if(barOpt.isEmpty()){
                throw new ObjectNotFoundException(TABLE_FIND_BAR_BY_ID_ERROR_CODE, this.getClass(),
                        ExceptionConstant.TABLE_FIND_BAR_BY_ID_ERROR);
            }

            table.setBar(barOpt.get());
            table = this.persist(table);
            return ResponseEntity.ok((BarTableDTO)MapperUtils.map(table, BarTableDTO.class));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR_CODE, this.getClass(),
                    ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR);
        }
    }

    private BarTable persist(BarTable table){
        return this.repository.save(table);
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
    public ResponseEntity<BarTableDTO> findById(Long id) {

        BarTableDTO response = null;

        try {
            Optional<BarTable> table = this.repository.findById(id);

            if(table.isPresent()){
                BarTable tbl = table.get();
                response = BarTableDTO.convert(tbl);
            }

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new ObjectNotFoundException(ExceptionConstant.TABLE_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public ResponseEntity<BarTableDTO> active(Long id) {
        try {
            this.repository.updateActive(id);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_UPDATE_ACTIVE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_UPDATE_ACTIVE_ERROR);
        }
        return this.findById(id);
    }

    @Override
    public ResponseEntity<ResponseDTO> activeByList(LongListDTO request){

        try {
            List<Long> requestList = request.getList();

            this.repository.updateActiveList(requestList);

            List<BarTableDTO> tableList = this.findByIdIn(requestList);

            return ResponseEntity.ok(new GenericRsDTO<>(SUCCESS_CODE,String.format(TABLES_FINDED,tableList.size()),tableList));

        }catch (Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_UPDATE_LIST_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_UPDATE_LIST_ERROR);
        }

    }

    @Override
    public List<BarTableDTO> findByIdIn(List<Long> list){

        Optional<List<BarTable>> tablesOpt = this.findIn(list);

        List<BarTableDTO> tableList = new ArrayList<>();

        if(tablesOpt.isPresent()){
            List<BarTable> tables = tablesOpt.get();
            tables.forEach(t -> tableList.add(BarTableDTO.convert(t)));
        }

        return tableList;
    }

    @Override
    public Optional<List<BarTable>> findIn(List<Long> list){

        Optional<List<BarTable>> tablesOpt;

        try {
            tablesOpt = this.repository.findByIdIn(list);
        }catch (Exception e){
            throw new UnauthorizedException(ExceptionConstant.TABLE_FIND_LIST_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_FIND_LIST_ERROR);
        }
        return tablesOpt;
    }

    @Override
    public ResponseEntity<ResponseDTO> createByQuantity(TableQuantityDTO request) {

        Bar bar;
        List<BarTable> list = new ArrayList<>();
        Long quantity = request.getQuantity();
        Long actual;
        List<IPBarTableDTO> response = new ArrayList<>();

        bar = this.getBarById(request.getBar());

        actual = this.countByBar(bar.getId());

        for (long i = actual + 1; i < actual + quantity + 1; i++) {

            BarTable table = BarTable.builder().bar(bar).number(i).active(true).build();
            table = this.generateNewCode(table);

            list.add(table);
        }

        this.saveAll(list).forEach(
                tbl -> response.add((IPBarTableDTO)MapperUtils.map(tbl, IPBarTableDTO.class))
        );

        return ResponseEntity.ok(new GenericRsDTO<>(SUCCESS_CODE,String.format(TABLES_CREATED_BY_QUANTITY,quantity),response));
    }

    @Override
    public BarTable generateNewCode(BarTable table){
        table.setQrCode(EncodeUtils.generateCode());
        return table;
    }

    @Override
    public List<BarTable> saveAll(List<BarTable> list){

        try {
            return this.repository.saveAll(list);
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

    @Override
    public ResponseEntity<ResponseDTO> generateCodesByList(LongListDTO request) {

        Optional<List<BarTable>> tableList = this.findIn(request.getList());

        List<BarTable> tables;
        List<BarTableDTO> tablesDto = new ArrayList<>();

        if(tableList.isPresent()){
            tables = tableList.get();
            for (BarTable table:tables) {
                table.setQrCode(EncodeUtils.generateCode());
                table = this.persist(table);
                tablesDto.add(BarTableDTO.convert(table));
            }
        }

        return ResponseEntity.ok(new GenericRsDTO<>(SUCCESS_CODE, String.format(TABLES_UPDATES_BY_QUANTITY, tablesDto.size()), tablesDto));
    }

    @Override
    public IPBarTable findByCode(String code) {
        Optional<IPBarTable> table = this.repository.findByQrCode(code);

        if(table.isEmpty()){
            throw new ObjectNotFoundException(ExceptionConstant.TABLE_FIND_BY_CODE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_FIND_BY_CODE_ERROR);
        }

        return table.get();
    }

    @Override
    public ResponseEntity<Boolean> isActiveByQrCode(String qrCode) {

        Optional<BarTable> barTableOpt;

        try {
            barTableOpt = this.repository.findActiveByQrCode(qrCode);
        }catch (Exception e){
            throw new BadRequestException(ExceptionConstant.TABLE_BARFIND_BY_CODE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.TABLE_BARFIND_BY_CODE_ERROR);
        }

        if (barTableOpt.isEmpty())
            return ResponseEntity.ok(false);

        Bar bar = barTableOpt.get().getBar();
        boolean response = true;

        if(bar.getOpenTime() != null && bar.getCloseTime() != null)
            response = DateUtils.isBetween(bar.getOpenTime(),bar.getCloseTime(),LocalTime.now());

        return ResponseEntity.ok(response);
    }
}
