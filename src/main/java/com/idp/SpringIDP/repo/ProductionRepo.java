package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepo extends JpaRepository<Production, Integer> {
    Production findByDocumentTableID(int id);
}
