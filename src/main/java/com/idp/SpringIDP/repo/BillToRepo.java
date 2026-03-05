package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.BillTo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillToRepo extends JpaRepository<BillTo, Integer> {
    BillTo findById(int id);
}
