package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Users;
import com.idp.SpringIDP.principal.UserPrincipal;
import com.idp.SpringIDP.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String companyID) throws UsernameNotFoundException {

        Users user = repo.findByCompanyID(companyID);
        if (user == null) {
            System.out.println("User not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
