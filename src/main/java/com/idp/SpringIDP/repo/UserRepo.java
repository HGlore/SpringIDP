package com.idp.SpringIDP.repo;

import com.idp.SpringIDP.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByCompanyID(String companyID);

    List<Users> findByStatus(int status);
}
