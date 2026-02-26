package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Images, Integer> {

    List<Images> findByStoredDate(String storedDate);
}
