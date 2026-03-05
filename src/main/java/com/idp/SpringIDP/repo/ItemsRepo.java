package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepo extends JpaRepository<Items, Integer> {
    List<Items> findByDocumentTableID(int id);
}
