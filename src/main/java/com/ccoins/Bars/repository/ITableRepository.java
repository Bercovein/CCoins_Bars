package com.ccoins.Bars.repository;

import com.ccoins.Bars.model.BarTable;
import com.ccoins.Bars.model.projection.IPBarTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITableRepository extends JpaRepository<BarTable, Long> {

    Optional<List<IPBarTable>> findByBarId(Long bar);

    Optional<List<IPBarTable>> findByBarIdAndActive(Long barId, boolean state);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tables set active = IF(active IS TRUE, FALSE, TRUE) where id = :id",nativeQuery = true)
    int updateActive(@Param("id") Long id);

//    @Query("SELECT COUNT(t) from Table t where t.bar.id = :bar")
    Long countByBarId(Long bar);


}
