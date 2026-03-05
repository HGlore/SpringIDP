package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Instructions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionsRepo extends JpaRepository<Instructions, Integer> {
    Instructions findById(int id);
}
