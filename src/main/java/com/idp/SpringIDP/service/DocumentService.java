package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Document;
import com.idp.SpringIDP.repo.DocumentRepo;
import com.idp.SpringIDP.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepo documentRepo;
    private final UserRepo userRepo;

    public List<Document> getForEntryList(String companyID) {
        return documentRepo.findByCompanyIDAndStatus(companyID, 1);
    }

    public boolean isHaveOngoingForEntry(String companyID) {
        var user = userRepo.findByCompanyID(companyID);
        if (!"Entry".equals(user.getRole())) {
            return true;
        }

        return documentRepo.existsOngoingEntry(companyID, 1);
    }
}
