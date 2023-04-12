package com.ccoins.bars.repository;

import com.ccoins.bars.model.BarHour;
import com.ccoins.bars.model.projection.IPBarHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBarHoursRepository extends JpaRepository<BarHour, Long> {

    List<IPBarHour> findAllProjectedByBarId(Long barId);
}
