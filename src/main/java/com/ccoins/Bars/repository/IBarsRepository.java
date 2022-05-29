package com.ccoins.Bars.repository;

import com.ccoins.Bars.model.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBarsRepository extends JpaRepository<Bar, Long> {

    Bar save(Bar bar);

    Optional<List<Bar>> findByOwner(Long ownerId);

    @Modifying
    @Query("UPDATE Bar set active = IF(active IS TRUE, FALSE, TRUE) where id = :id")
    int updateActive(@Param("id") Long id);
}
