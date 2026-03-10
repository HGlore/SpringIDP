package com.idp.SpringIDP.controller;

import com.idp.SpringIDP.dto.DocumentDTO;
import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.entity.Document;
import com.idp.SpringIDP.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"http://localhost:3000", "http://192.168.23.58:3000",
                "http://localhost:5173", "http://192.168.23.58:5173"},
        allowCredentials = "true")
public class EntryController {

    private final ImageService imageService;
    private final DocumentService docService;
    private final BillToService billToService;
    private final ConsigneeService consigneeService;
    private final InstructionsService instructionsService;
    private final ItemsService itemsService;
    private final ProductionService prodService;
    private final ShipperService shipperService;
    private final TotalsService totalsService;

    @GetMapping("/api/me/entries-status")
    public ResponseEntity<ImageDTO> getEntriesStatus(@RequestParam String storedDate,
                                                     Authentication authentication) throws Exception {

        if (authentication.isAuthenticated()) {
            System.out.println("Date Received: " + storedDate);
            System.out.println("CompanyID: " + authentication.getName());

            ImageDTO response = imageService.getImagesStatusOf(storedDate, authentication.getName());
            return ResponseEntity.ok(response);
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    @PostMapping("/api/me/assignments")
    public ResponseEntity<String> assignedAnImage(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            try {
                if (docService.isHaveOngoingForEntry(authentication.getName())) {
                    return ResponseEntity.ok("409 Conflict");
                }

                imageService.requestImage(authentication.getName());
                return ResponseEntity.ok("200 OK");
            } catch (Exception err) {
                throw new RuntimeException("403 Forbidden");
            }
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    @GetMapping("/api/me/entries")
    public ResponseEntity<List<DocumentDTO>> getEntryData(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            List<DocumentDTO> dataDTO = new ArrayList<>();
            String companyID = authentication.getName();

            var documentList = docService.getForEntryList(companyID);

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

    @GetMapping("/api/me/ongoing")
    public ResponseEntity<Boolean> checkForRequest(Authentication authentication) {
        String companyID = authentication.getName();
        return ResponseEntity.ok(docService.isHaveOngoingForEntry(companyID));
    }

    @Value("${file.image-dir}")
    private String imageDir;

    @GetMapping("/api/me/entry-image")
    public ResponseEntity<Resource> getEntryImage(@RequestParam String imageName) {
        try {
            Path imagePath = Paths.get(imageDir).resolve(imageName);

            Resource resource = new UrlResource(imagePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
