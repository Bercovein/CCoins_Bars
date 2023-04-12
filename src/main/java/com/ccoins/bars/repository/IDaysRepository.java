package com.ccoins.bars.repository;

import com.ccoins.bars.model.Day;
import com.ccoins.bars.model.projection.IPDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDaysRepository extends JpaRepository<Day, Long> {

    List<IPDay> findAllProjectedBy();
}
