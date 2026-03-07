package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Document;
import com.idp.SpringIDP.repo.DocumentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepo documentRepo;

    public List<Document> getDocumentDataList(String companyID) {
        return documentRepo.findByCompanyID(companyID);
    }
}
