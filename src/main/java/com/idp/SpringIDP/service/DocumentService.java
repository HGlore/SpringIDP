package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Document;
import com.idp.SpringIDP.repo.DocumentRepo;
import com.idp.SpringIDP.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepo documentRepo;
    private final UserRepo userRepo;

    public Document getForEntry(int id) {
        return documentRepo.findByStoredImageTableIDAndStatus(id, 1);
    }

    public boolean isHaveOngoingForEntry(String companyID) {
        var user = userRepo.findByCompanyID(companyID);
        if (!"Entry".equals(user.getRole())) {
            return true;
        }

        return documentRepo.existsOngoingEntry(companyID, 1);
    }
}
