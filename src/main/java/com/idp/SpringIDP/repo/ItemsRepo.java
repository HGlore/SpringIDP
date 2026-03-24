package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepo extends JpaRepository<Items, Integer> {
    @Query("SELECT i FROM Items i WHERE i.id = :id AND archive = 0")
    List<Items> findByDocumentTableID(@Param("id") int id);

    Items findById(int id);
}
