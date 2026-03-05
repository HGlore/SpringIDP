package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Totals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalsRepo extends JpaRepository<Totals, Integer> {
    Totals findById(int id);
}
