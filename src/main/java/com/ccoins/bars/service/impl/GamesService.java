package com.ccoins.bars.service.impl;

import com.ccoins.bars.dto.GameDTO;
import com.ccoins.bars.dto.GameTypeDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.exceptions.ObjectNotFoundException;
import com.ccoins.bars.exceptions.UnauthorizedException;
import com.ccoins.bars.exceptions.constant.ExceptionConstant;
import com.ccoins.bars.model.Bar;
import com.ccoins.bars.model.Game;
import com.ccoins.bars.model.GameType;
import com.ccoins.bars.model.projection.IPGame;
import com.ccoins.bars.repository.IBarsRepository;
import com.ccoins.bars.repository.IGamesRepository;
import com.ccoins.bars.repository.IGamesTypesRepository;
import com.ccoins.bars.service.IGamesService;
import com.ccoins.bars.utils.MapperUtils;
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

    private final IGamesRepository gamesRepository;
    private final IGamesTypesRepository typesRepository;
    private final IBarsRepository barRepository;
    
    @Autowired
    public GamesService(IGamesRepository gamesRepository, IGamesTypesRepository typesRepository, IBarsRepository barRepository) {
        this.gamesRepository = gamesRepository;
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
            game = this.gamesRepository.save(game);
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
            opt = this.gamesRepository.findByBarId(id);
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
            Optional<IPGame> game = this.gamesRepository.findProjectedById(id);
            return ResponseEntity.ok((GameDTO)MapperUtils.map(game,GameDTO.class));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.GAME_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public ResponseEntity<GameDTO> active(Long id) {

        try {
            this.gamesRepository.updateActive(id);
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

    @Override
    public ResponseEntity<GameDTO> findVotingGameByBarId(Long barId) {

        GameDTO response = null;

        Optional<Game> gameOptional = this.gamesRepository.findByBarIdAndGameTypeName(barId, "VOTE");

        if (gameOptional.isPresent()){
            response = GameDTO.convert(gameOptional.get());
        }

        return ResponseEntity.ok(response);
    }
}
