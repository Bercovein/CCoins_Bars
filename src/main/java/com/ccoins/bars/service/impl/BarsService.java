package com.ccoins.bars.service.impl;

import com.ccoins.bars.dto.*;
import com.ccoins.bars.exceptions.UnauthorizedException;
import com.ccoins.bars.exceptions.constant.ExceptionConstant;
import com.ccoins.bars.model.Bar;
import com.ccoins.bars.model.Game;
import com.ccoins.bars.model.projection.IPBar;
import com.ccoins.bars.repository.IBarsRepository;
import com.ccoins.bars.repository.ITableRepository;
import com.ccoins.bars.service.IBarsService;
import com.ccoins.bars.service.IDaysService;
import com.ccoins.bars.service.IGamesService;
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

    private final IBarsRepository barRepository;
    private final ITableRepository tableRepository;
    private final IDaysService daysService;
    private final IGamesService gamesService;

    @Autowired
    public BarsService(IBarsRepository barRepository, ITableRepository tableRepository, IDaysService daysService, IGamesService gamesService) {
        this.barRepository = barRepository;
        this.tableRepository = tableRepository;
        this.daysService = daysService;
        this.gamesService = gamesService;
    }

    @Override
    public ResponseEntity<BarDTO> saveOrUpdate(BarDTO barDTO) {

        try {
            Bar bar = this.barRepository.save(this.convert(barDTO));
            List<BarHourDTO> hours = new ArrayList<>();

            if(barDTO.getHours() != null && !barDTO.getHours().isEmpty())
                hours = this.daysService.saveOrUpdate(ListDTO.builder().list(barDTO.getHours()).build());

            this.gamesService.addVoteGameToBarIfDoNotHave(bar);
            this.gamesService.addCodeGameToBarIfDoNotHave(bar);

            BarDTO response = this.convert(bar);
            response.setHours(hours);

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_CREATE_OR_UPDATE_ERROR);
        }
    }

    @Override
    public ResponseEntity<ListDTO> findAllByOwner(Long ownerId) {

        ListDTO response = new ListDTO(new ArrayList<>());

        try {
            Optional<List<IPBar>> barsOpt = this.barRepository.findByOwner(ownerId);

            barsOpt.ifPresent(response::setList);

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
            Optional<Bar> bar = this.barRepository.findById(id);

            if(bar.isEmpty()){
                return ResponseEntity.ok(null);
            }

            BarDTO response = this.convert(bar.get());

            ListDTO list = this.daysService.getHoursByBar(id).getBody();

            response.setHours((List<BarHourDTO>) list.getList());

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public ResponseEntity<BarDTO> active(Long id) {

        try {
            this.barRepository.updateActive(id);
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
        return MapperUtils.map(barDTO,Bar.class);
    }

    private BarDTO convert(Bar bar){
        return MapperUtils.map(bar,BarDTO.class);
    }

    @Override
    public ResponseEntity<IdDTO> getBarIdByParty(Long id){
        try{
            IdDTO response = IdDTO.builder().id(
                    this.barRepository.getBarIdByParty(id).orElse(null)
            ).build();
            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_GET_BAR_ID_BY_PARTY_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_GET_BAR_ID_BY_PARTY_ERROR);
        }
    }

    @Override
    public ResponseEntity<BarDTO> getBarByGame(Long id) {

        Optional<Game> gameOptional = this.gamesService.findGameBarById(id);
        return ResponseEntity.ok(this.convert(gameOptional.get().getBar()));
    }
}
