package com.idp.SpringIDP.controller;

import com.idp.SpringIDP.dto.DocumentDTO;
import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.entity.Document;
import com.idp.SpringIDP.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/me/ids-entries")
    public ResponseEntity<List<Integer>> getEntryIDs(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(imageService.getEntriesIDs(authentication.getName()));
        }
        throw new BadCredentialsException("401 Unauthorized");
    }

    @GetMapping("/api/me/entries/{id}")
    public ResponseEntity<DocumentDTO> getEntryData(@PathVariable Integer id, Authentication authentication) {

        if (authentication.isAuthenticated()) {

            var document = docService.getForEntry(id);
            String imageName = imageService.getImageName(document.getStoredImageTableID());
            var shipper = shipperService.getShipper(document.getShipperTableID());
            var consignee = consigneeService.getConsignee(document.getConsigneeTableID());
            var billTo = billToService.getBillTo(document.getBillToTableID());
            var instructions = instructionsService.getInstruction(document.getInstructionTableID());
            var totals = totalsService.getTotals(document.getTotalsTableID());
            var itemsList = itemsService.getItemsList(document.getId());

            DocumentDTO dataDTO = new DocumentDTO(
                    document,
                    imageName,
                    shipper,
                    consignee,
                    billTo,
                    instructions,
                    totals,
                    itemsList
            );

            return ResponseEntity.ok(dataDTO);
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    @PostMapping("/api/me/entries/batch")
    public ResponseEntity<List<DocumentDTO>> getEntryDataBatch(@RequestBody List<Integer> ids, Authentication authentication) {

        if (authentication.isAuthenticated()) {

            var dataDTO = new ArrayList<DocumentDTO>();

            System.out.println("IDS: " + ids);

            for (Integer id : ids) {

                var document = docService.getForEntry(id);

                if (document.getEntryStatus() == 0) {

                    String imageName = imageService.getImageName(document.getStoredImageTableID());
                    var shipper = shipperService.getShipper(document.getShipperTableID());
                    var consignee = consigneeService.getConsignee(document.getConsigneeTableID());
                    var billTo = billToService.getBillTo(document.getBillToTableID());
                    var instructions = instructionsService.getInstruction(document.getInstructionTableID());
                    var totals = totalsService.getTotals(document.getTotalsTableID());
                    var itemsList = itemsService.getItemsList(document.getId());

                    dataDTO.add(new DocumentDTO(
                            document,
                            imageName,
                            shipper,
                            consignee,
                            billTo,
                            instructions,
                            totals,
                            itemsList
                    ));

                    return ResponseEntity.ok(dataDTO);
                }
            }
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
    public ResponseEntity<Resource> getEntryImage(@RequestParam String imageName, Authentication authentication) {

        System.out.println("Imagename: " + imageName);

        if (authentication.isAuthenticated()) {
            try {
                Path imagePath = Paths.get(imageDir).resolve(imageName);
                System.out.println("ImagePath:" + imagePath);

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

        throw new BadCredentialsException("401 Unauthorized");
    }
}
