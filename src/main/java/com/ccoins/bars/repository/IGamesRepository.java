package com.ccoins.bars.repository;

import com.ccoins.bars.model.Game;
import com.ccoins.bars.model.projection.IPGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IGamesRepository extends JpaRepository<Game, Long> {

    Game save(Game game);

    Optional<IPGame> findProjectedById(Long id);

    Optional<List<IPGame>> findByBarId(Long id);

    Optional<List<IPGame>> findByBarIdAndActive(@Param("id")Long id,@Param("active") Boolean active);

    @Query("FROM Game where bar.id = :bar and gameType.name = :gameType and active = true")
    Optional<Game> findByBarIdAndGameTypeName(@Param("bar") Long bar, @Param("gameType") String gameType);

    @Transactional
    @Modifying
    @Query(value = "UPDATE games set active = IF(active IS TRUE, FALSE, TRUE) where id = :id", nativeQuery = true)
    int updateActive(@Param("id") Long id);
}
