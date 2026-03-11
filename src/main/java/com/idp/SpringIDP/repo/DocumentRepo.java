package com.idp.SpringIDP.repo;


import com.idp.SpringIDP.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Integer> {

    Document findByStoredImageTableID(int id);

    @Query("SELECT COUNT(d) > 0 FROM Document d WHERE d.companyID = :companyID AND d.status = :status AND d.archive = 0")
    boolean existsOngoingEntry(@Param("companyID") String companyID,
                               @Param("status") int status);

    @Query("SELECT d FROM Document d WHERE d.storedImageTableID = :id AND d.status = :status AND d.archive = 0")
    Document findByStoredImageTableIDAndStatus(@Param("id") int id,
                                               @Param("status") int status);
}
