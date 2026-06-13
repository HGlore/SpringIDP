package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.dto.RegisterDTO;
import com.idp.SpringIDP.entity.*;
import com.idp.SpringIDP.repo.*;
import org.hibernate.ReadOnlyMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final String registryKey = "f.sox.4545";
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UserRepo userRepo;

    /*@Autowired
    private UsersRegistryKeyRepo registryKeyRepo;*/

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    public void register(RegisterDTO registry) {
        try {
            // Map to Users for DTO
            Users newUser = new Users();
            newUser.setCompanyID(registry.getCompanyID());
            newUser.setRole(registry.getRole());
            newUser.setPassword(encoder.encode(registry.getPassword()));
            userRepo.save(newUser);

        } catch (Exception e) {
            System.out.println("Error : hererere" + e.getMessage());
            throw new RuntimeException("500 Internal Server Error" + e.getMessage());
        }
    }

    public boolean verifyKey(String regKey) {
        return regKey.equals(registryKey);
    }

    public Users verify(Users user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(
                        user.getCompanyID(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return user;
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    public String getAuthToken(Users user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(
                        user.getCompanyID(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getCompanyID());
        }

        throw new RuntimeException("401 Unauthorized");
    }

    public Users getUserData(String companyID) {
        return userRepo.findByCompanyID(companyID);
    }

    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    public List<Users> getFilteredUsers(int status) {
        return userRepo.findByStatus(status);
    }
}
