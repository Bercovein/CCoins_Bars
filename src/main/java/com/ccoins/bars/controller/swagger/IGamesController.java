package com.ccoins.bars.controller.swagger;

import com.ccoins.bars.dto.GameDTO;
import com.ccoins.bars.dto.ListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "GAMES")
public interface IGamesController {

    @ApiOperation(value = "Save or update bar")
    ResponseEntity<GameDTO> saveOrUpdate(@RequestBody GameDTO gameDTO);
    @ApiOperation(value = "Find list of games by Bar id")
    ResponseEntity<ListDTO> findAllByBar(@PathVariable("id") Long id);

    @ApiOperation(value = "Find all games actives by bar id")
    ResponseEntity<ListDTO> findAllActiveByBar(@PathVariable("id") Long id);

    @ApiOperation(value = "Find game by id")
    ResponseEntity<GameDTO> findById(@PathVariable("id") Long id);

    @ApiOperation(value = "Active/Unactive by id")
    ResponseEntity<GameDTO> active(@PathVariable("id") Long id);

    @ApiOperation(value = "Find all type of games")
    ResponseEntity<ListDTO> findAllTypes();

    @ApiOperation(value = "Find voting game by bar id")
    ResponseEntity<GameDTO> findVotingGameByBarId(@PathVariable("id") Long id);

    @ApiOperation(value = "Find game code by bar id")
    ResponseEntity<GameDTO> findCodeGameByBarId(@PathVariable("id") Long id);
}
