package com.ccoins.Bars.repository;

import com.ccoins.Bars.model.Game;
import com.ccoins.Bars.model.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGamesTypesRepository extends JpaRepository<GameType, Long> {

    Optional<List<Game>> findAll(Long id);

}
