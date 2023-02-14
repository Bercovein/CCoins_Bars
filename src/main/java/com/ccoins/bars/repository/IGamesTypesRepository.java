package com.ccoins.bars.repository;

import com.ccoins.bars.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGamesTypesRepository extends JpaRepository<GameType, Long> {

    List<GameType> findAll();

    GameType getByName(String value);
}
