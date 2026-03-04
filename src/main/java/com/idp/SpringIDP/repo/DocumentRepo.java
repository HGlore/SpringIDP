package com.idp.SpringIDP.repo;


import com.idp.SpringIDP.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Integer> {

    Document findById(int id);
}
