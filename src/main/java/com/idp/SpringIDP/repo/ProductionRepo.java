package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductionRepo extends JpaRepository<Production, Integer> {
    Production findByDocumentTableID(int id);

    @Query("SELECT p FROM Production p WHERE p.companyID = :companyID AND p.productionDate = :productionDate AND p.status = 2 AND p.archive = 0")
    List<Production> findByCompanyIDAndProductionDate(@Param("companyID") String companyID,
                                                      @Param("productionDate") String productionDate);

    @Query("SELECT p FROM Production p WHERE p.productionDate = :productionDate AND p.status = 2 AND p.archive = 0")
    List<Production> findByProductionDate(@Param("productionDate") String storeDate);

    @Query("SELECT p FROM Production p " +
            "WHERE p.documentTableID = :documentTableID " +
            "AND p.companyID = :companyID " +
            "AND p.productionDate = :productionDate " +
            "AND p.status = 1 AND p.archive = 0")
    Production findByDocumentTableDAndCompanyIDAndProductionDate(
            @Param("documentTableID") int documentTableID,
            @Param("companyID") String companyID,
            @Param("productionDate") String storeDate);
}
