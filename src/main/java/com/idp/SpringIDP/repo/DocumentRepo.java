package com.idp.SpringIDP.repo;


import com.idp.SpringIDP.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Integer> {

    Document findByStoredImageTableID(int id);

    @Query("SELECT d FROM Document d WHERE d.companyID = :companyID AND d.status = 1 ORDER BY d.id")
    List<Document> findByCompanyID(@Param("companyID") String companyID);
}
