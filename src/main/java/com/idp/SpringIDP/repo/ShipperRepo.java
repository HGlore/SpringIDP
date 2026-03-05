package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepo extends JpaRepository<Shipper, Integer> {
    Shipper findById(int id);
}
