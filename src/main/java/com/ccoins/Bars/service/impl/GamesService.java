package com.ccoins.Bars.service.impl;

import com.ccoins.Bars.dto.GameDTO;
import com.ccoins.Bars.dto.GameTypeDTO;
import com.ccoins.Bars.dto.ListDTO;
import com.ccoins.Bars.exceptions.ObjectNotFoundException;
import com.ccoins.Bars.exceptions.UnauthorizedException;
import com.ccoins.Bars.exceptions.constant.ExceptionConstant;
import com.ccoins.Bars.model.Bar;
import com.ccoins.Bars.model.Game;
import com.ccoins.Bars.model.GameType;
import com.ccoins.Bars.model.projection.IPGame;
import com.ccoins.Bars.repository.IBarsRepository;
import com.ccoins.Bars.repository.IGamesRepository;
import com.ccoins.Bars.repository.IGamesTypesRepository;
import com.ccoins.Bars.service.IGamesService;
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
public class GamesService implements IGamesService {

    private final IGamesRepository repository;
    private final IGamesTypesRepository typesRepository;
    private final IBarsRepository barRepository;
    
    @Autowired
    public GamesService(IGamesRepository repository, IGamesTypesRepository typesRepository, IBarsRepository barRepository) {
        this.repository = repository;
        this.typesRepository = typesRepository;
        this.barRepository = barRepository;
    }

    @Override
    public ResponseEntity<GameDTO> saveOrUpdate(GameDTO gameDTO) {

        Optional<Bar> barOpt;
        Game game;
        
        try {
            game = (Game) MapperUtils.map(gameDTO, Game.class);
            barOpt = this.barRepository.findById(gameDTO.getBar());

            if(barOpt.isEmpty()){
                throw new ObjectNotFoundException(ExceptionConstant.GAME_FIND_BAR_BY_ID_ERROR_CODE, this.getClass(),
                        ExceptionConstant.GAME_FIND_BAR_BY_ID_ERROR);
            }

            game.setBar(barOpt.get());
            game = this.repository.save(game);
            return ResponseEntity.ok(GameDTO.convert(game));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.GAME_CREATE_OR_UPDATE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_CREATE_OR_UPDATE_ERROR);
        }
    }

    @Override
    public ResponseEntity<ListDTO> findAllByBar(Long id) {

        ListDTO response = new ListDTO(new ArrayList<>());
        Optional<List<IPGame>> opt;

        try {
            opt = this.repository.findByBarId(id);
            opt.ifPresent(response::setList);

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.GAME_FIND_BY_BAR_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.GAME_FIND_BY_BAR_ERROR);
        }
    }

    @Override
    public ResponseEntity<GameDTO> findById(Long id) {

        try {
            Optional<IPGame> game = this.repository.findProjectedById(id);
            return ResponseEntity.ok((GameDTO)MapperUtils.map(game,GameDTO.class));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.GAME_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public ResponseEntity<GameDTO> active(Long id) {

        try {
            this.repository.updateActive(id);
        }catch(Exception e){
            e.printStackTrace();
            throw new UnauthorizedException(ExceptionConstant.GAME_UPDATE_ACTIVE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_UPDATE_ACTIVE_ERROR);
        }
        return this.findById(id);
    }

    @Override
    public ResponseEntity<ListDTO> findAllTypes() {

        List<GameType> list;

        try {
            list = this.typesRepository.findAll();

            return ResponseEntity.ok(ListDTO.builder().list(MapperUtils.mapList(list, GameTypeDTO.class)).build());
        }catch(Exception e){
            e.printStackTrace();
            throw new UnauthorizedException(ExceptionConstant.GAME_UPDATE_ACTIVE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_UPDATE_ACTIVE_ERROR);
        }
    }
}
