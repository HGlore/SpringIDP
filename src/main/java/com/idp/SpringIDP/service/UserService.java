package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.entity.Images;
import com.idp.SpringIDP.entity.Users;
import com.idp.SpringIDP.repo.DocumentRepo;
import com.idp.SpringIDP.repo.ImageRepo;
import com.idp.SpringIDP.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private DocumentRepo documentRepo;

    /*@Autowired
    private UsersRegistryKeyRepo registryKeyRepo;*/

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /*public UserResponseDTO register(UsersRegistryKeyDTO userDTO) {

        RegistryKey key = registryKeyRepo.findByRegistryKey(userDTO.getKey());
        if (key == null) {
            throw new RuntimeException("Invalid registry key");
        } else {

            // Map to Users for DTO
            Users user = new Users();
            user.setUsername(userDTO.getUsername());
            user.setPassword(encoder.encode(userDTO.getPassword()));
            repo.save(user);

            return new UserResponseDTO(user.getUsername(), "200 OK");
        }
    }*/

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

    public ImageDTO getImagesOf(String storeDate) throws Exception {
        var images = imageRepo.findByStoredDate(storeDate);

        if (images == null || images.isEmpty()) {
            return new ImageDTO(storeDate, 0, 0, 0);
        }

        int totalQueue = (int) images.stream()
                .filter(img -> img.getStatus() == 0 && img.getAiResponse() == 1)
                .count();
        int newImages = images.size();
        int billedImages = (int) images.stream().filter(img -> img.getStatus() == 2).count();

        return new ImageDTO(storeDate, totalQueue, newImages, billedImages);
    }
}
