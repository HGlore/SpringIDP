package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByCompanyID(String companyID);
}
