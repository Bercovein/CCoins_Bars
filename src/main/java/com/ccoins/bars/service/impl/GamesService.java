package com.ccoins.bars.service.impl;

import com.ccoins.bars.configuration.CodeConfig;
import com.ccoins.bars.configuration.VotingConfig;
import com.ccoins.bars.dto.GameDTO;
import com.ccoins.bars.dto.GameTypeDTO;
import com.ccoins.bars.dto.ListDTO;
import com.ccoins.bars.exceptions.BadRequestException;
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
import com.ccoins.bars.utils.enums.GameEnum;
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

    private final VotingConfig votingConfig;
    private final CodeConfig codeConfig;

    @Autowired
    public GamesService(IGamesRepository gamesRepository, IGamesTypesRepository typesRepository, IBarsRepository barRepository, VotingConfig votingConfig, CodeConfig codeConfig) {
        this.gamesRepository = gamesRepository;
        this.typesRepository = typesRepository;
        this.barRepository = barRepository;
        this.votingConfig = votingConfig;
        this.codeConfig = codeConfig;
    }

    @Override
    public ResponseEntity<GameDTO> saveOrUpdate(GameDTO gameDTO) {

        Optional<Bar> barOpt;
        Game game;
        
        try {
            game = MapperUtils.map(gameDTO, Game.class);
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
            return ResponseEntity.ok(MapperUtils.map(game,GameDTO.class));
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.GAME_FIND_BY_ID_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_FIND_BY_ID_ERROR);
        }
    }

    @Override
    public Optional<Game> findGameBarById(Long id){
        try{
            return this.gamesRepository.findById(id);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.BAR_GET_BAR_ID_BY_PARTY_ERROR_CODE,
                    this.getClass(), ExceptionConstant.BAR_GET_BAR_ID_BY_PARTY_ERROR);
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
            throw new UnauthorizedException(ExceptionConstant.GAME_UPDATE_ACTIVE_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_UPDATE_ACTIVE_ERROR);
        }
    }

    @Override
    public ResponseEntity<GameDTO> findGameByBarIdAndGame(Long barId, String game) {

        GameDTO response = null;

        try {
            Optional<Game> gameOptional = this.gamesRepository.findByBarIdAndGameTypeName(barId, game);

            if (gameOptional.isPresent()) {
                response = GameDTO.convert(gameOptional.get());
            }
        }catch(Exception e){
            throw new ObjectNotFoundException(ExceptionConstant.GAME_BY_BAR_ERROR_CODE,
                    this.getClass(), ExceptionConstant.GAME_BY_BAR_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ListDTO> findAllActiveByBar(Long id) {
        ListDTO response = new ListDTO(new ArrayList<>());
        Optional<List<IPGame>> opt;

        try {
            opt = this.gamesRepository.findByBarIdAndActive(id,true);
            opt.ifPresent(response::setList);

            return ResponseEntity.ok(response);
        }catch(Exception e){
            throw new UnauthorizedException(ExceptionConstant.GAME_FIND_BY_BAR_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.GAME_FIND_BY_BAR_ERROR);
        }
    }

    @Override
    public void addVoteGameToBarIfDoNotHave(Bar bar){

        try{
            Optional<Game> gameOpt = this.gamesRepository.findByBarIdAndGameTypeName(bar.getId(), GameEnum.VOTE.getValue());

            if(gameOpt.isEmpty()){
                this.gamesRepository.save(Game.builder()
                        .bar(bar)
                        .active(true)
                        .name(votingConfig.getName())
                        .rules(votingConfig.getRules())
                        .gameType(this.typesRepository.getByName(GameEnum.VOTE.getValue()))
                        .closeTime(bar.getCloseTime())
                        .openTime(bar.getOpenTime())
                        .points(10L)
                        .build());
            }
        }catch (Exception e){
            throw new BadRequestException(ExceptionConstant.VOTE_GAME_TO_BAR_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.VOTE_GAME_TO_BAR_ERROR);
        }
    }

    @Override
    public void addCodeGameToBarIfDoNotHave(Bar bar){

        try {
            Optional<Game> gameOpt = this.gamesRepository.findByBarIdAndGameTypeName(bar.getId(), GameEnum.CODE.getValue());

            if (gameOpt.isEmpty()) {
                this.gamesRepository.save(Game.builder()
                        .bar(bar)
                        .active(true)
                        .name(codeConfig.getName())
                        .rules(codeConfig.getRules())
                        .gameType(this.typesRepository.getByName(GameEnum.CODE.getValue()))
                        .closeTime(bar.getCloseTime())
                        .openTime(bar.getOpenTime())
                        .points(null)
                        .build());
            }
        }catch (Exception e){
            throw new BadRequestException(ExceptionConstant.CODE_GAME_TO_BAR_ERROR_CODE,
                    this.getClass(),
                    ExceptionConstant.CODE_GAME_TO_BAR_ERROR);
        }
    }

}
