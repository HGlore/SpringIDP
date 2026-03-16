package com.idp.SpringIDP.controller;

import com.idp.SpringIDP.dto.DocumentDTO;
import com.idp.SpringIDP.dto.EntriesBatchDTO;
import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.dto.OngoingEntriesDTO;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                var checkEntries = docService.isHaveOngoingForEntry(authentication.getName());
                if (checkEntries.isOngoing()) {
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
    public ResponseEntity<DocumentDTO> getEntryData(@PathVariable Integer id,
                                                    @RequestParam String date,
                                                    Authentication authentication) {

        if (authentication.isAuthenticated()) {
            var document = docService.getForEntry(id);
            String storedDate = imageService.getStoredDate(document.getStoredImageTableID());

            if (storedDate.equals(date)) {

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
            } else {
                return ResponseEntity.ok(new DocumentDTO());
            }
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    @PostMapping("/api/me/entries/batch")
    public ResponseEntity<List<DocumentDTO>> getEntryDataBatch(@RequestBody EntriesBatchDTO batchData,
                                                               Authentication authentication) {

        if (authentication.isAuthenticated()) {
            var dataDTO = new ArrayList<DocumentDTO>();
            var ids = batchData.getIds();
            String date = batchData.getDate();

            for (Integer id : ids) {

                var document = docService.getForEntry(id);
                String storedDate = imageService.getStoredDate(document.getStoredImageTableID());

                if (document.getEntryStatus() == 0 && storedDate.equals(date)) {

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
            return ResponseEntity.ok(new ArrayList<>());
        }

        throw new BadCredentialsException("401 Unauthorized");
    }

    @GetMapping("/api/me/ongoing")
    public ResponseEntity<OngoingEntriesDTO> checkForRequest(Authentication authentication) {
        String companyID = authentication.getName();
        return ResponseEntity.ok(docService.isHaveOngoingForEntry(companyID));
    }

    @Value("${file.image-dir}")
    private String imageDir;

    @GetMapping("/api/me/entry-image")
    public ResponseEntity<Resource> getEntryImage(@RequestParam String imageName, Authentication authentication) {

        if (authentication.isAuthenticated()) {
            try {
                Path imagePath = Paths.get(imageDir).resolve(imageName);
                Resource resource = new UrlResource(imagePath.toUri());

                if (!resource.exists()) {
                    return ResponseEntity.notFound().build();
                }

                String contentType = Files.probeContentType(imagePath);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }

        throw new BadCredentialsException("401 Unauthorized");
    }
}
