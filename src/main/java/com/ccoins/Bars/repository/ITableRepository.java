package com.ccoins.Bars.repository;

import com.ccoins.Bars.model.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITableRepository extends JpaRepository<Bar, Long> {

}
