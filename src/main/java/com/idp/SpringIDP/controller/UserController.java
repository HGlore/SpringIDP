package com.idp.SpringIDP.controller;

import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.dto.UserDTO;
import com.idp.SpringIDP.entity.Images;
import com.idp.SpringIDP.entity.Users;
import com.idp.SpringIDP.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(
        origins = {"http://localhost:3000", "http://192.168.23.58:3000",
                "http://localhost:5173", "http://192.168.23.58:5173"},
        allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users user, HttpServletResponse response) {
        String generatedToken = service.verify(user);

        ResponseCookie cookie = ResponseCookie.from("auth", generatedToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMinutes(30))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        Map<String, Object> responseBody = new HashMap<>();
        Users userData = service.getUserData(user.getCompanyID());
        responseBody.put("companyID", userData.getCompanyID());
        responseBody.put("role", userData.getRole());
        responseBody.put("status", "200 OK");
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/userout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        // Clear the 'auth' cookie by setting maxAge to 0
        ResponseCookie cookie = ResponseCookie.from("auth", "")
                .httpOnly(true)
                .secure(false) // same as login
                .sameSite("Lax")
                .path("/")
                .maxAge(0) // expire immediately
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/getImages")
    public ResponseEntity<ImageDTO> getImages(@RequestParam String storedDate) throws Exception {
        System.out.println("Date Received: " + storedDate);

        ImageDTO response = service.getImagesOf(storedDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/profile/image")
    public ResponseEntity<Resource> userProfile(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        String username = authentication.getName();
        Users userData = service.getUserData(username);

        // Build path inside resources/static/images
        String userImagePath = "static/images/" + userData.getCompanyID() + ".jpg";
        String defaultImagePath = "static/images/defaultPF.jpg";

        Resource resource = new ClassPathResource(userImagePath);

        if (!resource.exists()) {
            resource = new ClassPathResource(defaultImagePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @GetMapping("/api/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails != null) {
            Users userData = service.getUserData(userDetails.getUsername()); // username or companyID
            return ResponseEntity.ok(new UserDTO(userData.getCompanyID(),
                    userData.getRole(), "200 OK"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
