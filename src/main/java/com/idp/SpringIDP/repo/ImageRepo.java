package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Images, Integer> {

    List<Images> findByStoredDate(String storedDate);

    @Query("SELECT i FROM Images i WHERE i.status = 0 AND i.aiResponse = 1 LIMIT 5")
    List<Images> findTop5ByOrderByIdAsc();

    @Query("SELECT i FROM Images i WHERE i.id = :id")
    Images findById(@Param("id") int id);

    @Query("SELECT i.id FROM Images i WHERE i.assignedTo = :assignedTo AND i.status = :status AND i.archive = 0")
    List<Integer> findByAssignedToAndStatus(@Param("assignedTo") String companyID,
                                            @Param("status") int status);

    @Query("SELECT i.id FROM Images i WHERE i.assignedTo = :assignedTo AND storedDate = :storedDate AND i.status = 1 AND i.archive = 0")
    Images findByStoredDateAndAssignedTo(@Param("assignedTo") String companyID,
                                         @Param("storedDate") String storedDate);

    @Query("SELECT i FROM Images i WHERE i.assignedTo = :assignedTo AND i.status = 1 AND i.archive = 0 LIMIT 1")
    Images findByAssignedToWithStoredDate(@Param("assignedTo") String companyID);
}
