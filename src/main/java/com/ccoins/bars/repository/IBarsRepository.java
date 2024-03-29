package com.ccoins.bars.repository;

import com.ccoins.bars.model.Bar;
import com.ccoins.bars.model.projection.IPBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBarsRepository extends JpaRepository<Bar, Long> {

    Bar save(Bar bar);

    Optional<List<IPBar>> findByOwner(Long ownerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE bars set active = IF(active IS TRUE, FALSE, TRUE) where id = :id", nativeQuery = true)
    int updateActive(@Param("id") Long id);

    @Query(value = "select b.id from parties p" +
            " inner join bar_tables bt on bt.id = p.fk_table" +
            " inner join bars b on b.id = bt.fk_bar" +
            " where p.id = :id", nativeQuery = true)
    Optional<Long> getBarIdByParty(@Param("id") Long id);
}
