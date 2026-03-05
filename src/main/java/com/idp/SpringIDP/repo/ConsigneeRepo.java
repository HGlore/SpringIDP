package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Consignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsigneeRepo extends JpaRepository<Consignee, Integer> {

    Consignee findById(int id);
}
