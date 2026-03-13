package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.OngoingEntriesDTO;
import com.idp.SpringIDP.entity.Document;
import com.idp.SpringIDP.repo.DocumentRepo;
import com.idp.SpringIDP.repo.ImageRepo;
import com.idp.SpringIDP.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepo documentRepo;
    private final ImageRepo imageRepo;
    private final UserRepo userRepo;

    public Document getForEntry(int id) {
        return documentRepo.findByStoredImageTableIDAndStatus(id, 1);
    }

    public OngoingEntriesDTO isHaveOngoingForEntry(String companyID) {
        var user = userRepo.findByCompanyID(companyID);
        if (!"Entry".equals(user.getRole())) {
            return new OngoingEntriesDTO(false, "");
        }

        System.out.println("Check Ongoing: " + companyID);

        if (documentRepo.existsOngoingEntry(companyID, 1)) {
            var image = imageRepo.findByAssignedToWithStoredDate(companyID);

            System.out.println("Ongoing Image:" + image);

            return new OngoingEntriesDTO(true, image.getStoredDate());
        } else {
            return new OngoingEntriesDTO(false, "");
        }
    }
}
