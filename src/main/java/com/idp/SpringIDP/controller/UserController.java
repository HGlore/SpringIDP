package com.idp.SpringIDP.controller;

import com.idp.SpringIDP.dto.DocumentDTO;
import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.dto.UserDTO;
import com.idp.SpringIDP.entity.BillTo;
import com.idp.SpringIDP.entity.Consignee;
import com.idp.SpringIDP.entity.Document;
import com.idp.SpringIDP.entity.Users;
import com.idp.SpringIDP.service.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"http://localhost:3000", "http://192.168.23.58:3000",
                "http://localhost:5173", "http://192.168.23.58:5173"},
        allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;
    private final DocumentService docService;
    private final BillToService billToService;
    private final ConsigneeService consigneeService;
    private final InstructionsService instructionsService;
    private final ItemsService itemsService;
    private final ProductionService prodService;
    private final ShipperService shipperService;
    private final TotalsService totalsService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users user, HttpServletResponse response) {

        var verifiedUser = userService.verify(user);

        if (verifiedUser != null) {
            String generatedToken = userService.getAuthToken(user);

            ResponseCookie cookie = ResponseCookie.from("auth", generatedToken)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofMinutes(30))
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            Map<String, Object> responseBody = new HashMap<>();
            Users userData = userService.getUserData(user.getCompanyID());
            responseBody.put("companyID", userData.getCompanyID());
            responseBody.put("role", userData.getRole());
            responseBody.put("status", "200 OK");
            return ResponseEntity.ok(responseBody);
        }

        throw new BadCredentialsException("401 Unauthorized");
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

        return ResponseEntity.ok("200 OK");
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDTO> getImages(@RequestBody Map<String, Object> payload, Authentication authentication) throws Exception {

        String storedDate = (String) payload.get("storedDate");
        // map/deserialized json userData fom payload
        ObjectMapper mapper = new ObjectMapper();
        var user = mapper.convertValue(payload.get("user"), UserDTO.class); // converValue(fromValue, toValueType/class);
        var userData = userService.getUserData(user.getCompanyID());

        if (userData != null && authentication.isAuthenticated()) {
            System.out.println("Date Received: " + storedDate);
            System.out.println("CompanyID: " + userData.getCompanyID());

            ImageDTO response = imageService.getImagesOf(storedDate);
            return ResponseEntity.ok(response);
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    @GetMapping("/api/profile/image")
    public ResponseEntity<Resource> userProfile(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        String username = authentication.getName();
        Users userData = userService.getUserData(username);

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
            Users userData = userService.getUserData(userDetails.getUsername()); // username or companyID
            return ResponseEntity.ok(new UserDTO(userData.getCompanyID(),
                    userData.getRole(), "200 OK"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/request")
    public ResponseEntity<?> assignedAnImage(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            try {
                imageService.requestImage(authentication.getName());
                return ResponseEntity.ok("200 OK");
            } catch (Exception err) {
                throw new RuntimeException("403 Forbidden");
            }
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    @PostMapping("/entry/data")
    public ResponseEntity<List<DocumentDTO>> getEntryData(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            List<DocumentDTO> dataDTO = new ArrayList<>();

            var documentList = docService.getDocumentDataList(authentication.getName());

            for (Document d : documentList) {
                String imageName = imageService.getImageName(d.getStoredImageTableID());
                var shipper = shipperService.getShipper(d.getShipperTableID());
                var consignee = consigneeService.getConsignee(d.getConsigneeTableID());
                var billTo = billToService.getBillTo(d.getBillToTableID());
                var instructions = instructionsService.getInstruction(d.getInstructionTableID());
                var totals = totalsService.getTotals(d.getTotalsTableID());
                var itemsList = itemsService.getItemsList(d.getId());

                dataDTO.add(new DocumentDTO(
                        d,
                        imageName,
                        shipper,
                        consignee,
                        billTo,
                        instructions,
                        totals,
                        itemsList
                ));
            }

            return ResponseEntity.ok(dataDTO);
        }

        throw new BadCredentialsException("401 Unauthorized");
    }
}
