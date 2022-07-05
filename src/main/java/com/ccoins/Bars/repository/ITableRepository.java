package com.ccoins.Bars.repository;

import com.ccoins.Bars.model.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITableRepository extends JpaRepository<BarTable, Long> {

    Optional<List<IPBarTable>> findByBarId(Long bar);

    Optional<List<IPBarTable>> findByBarIdAndActive(Long barId, boolean state);

    @Transactional
    @Modifying
    @Query(value = "UPDATE bar_tables set active = IF(active IS TRUE, FALSE, TRUE) where id = :id",nativeQuery = true)
    int updateActive(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE bar_tables set active = IF(active IS TRUE, FALSE, TRUE) where id in (:list)",nativeQuery = true)
    int updateActiveList(@Param("list") List<Long> list);

    Long countByBarId(Long bar);

    Optional<List<BarTable>> findByIdIn(List<Long> tables);

    @Query(value = "select t.* FROM bar_tables t WHERE t.fk_bar = :bar ORDER BY t.id DESC LIMIT :limit", nativeQuery = true)
    Optional<List<BarTable>> findByBarIdOrderByIdDescLimit(Long bar, Long limit);

    Optional<BarTable> findById(Long id);

}
