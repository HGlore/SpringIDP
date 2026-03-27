package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.DocumentDTO;
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

        if (documentRepo.existsOngoingEntry(companyID, 1)) {
            var image = imageRepo.findByAssignedToWithStoredDate(companyID);

            return new OngoingEntriesDTO(true, image.getStoredDate());
        } else {
            return new OngoingEntriesDTO(false, "");
        }
    }

    /* void */
    public void putDocumentData(DocumentDTO documentDTO) {

        var document = documentRepo.findByStoredImageTableID(documentDTO.getId());

        document.setStartTime(documentDTO.getStartTime());
        document.setEndTime(documentDTO.getEndTime());
        document.setImage(documentDTO.getImage());
        document.setAccountType(documentDTO.getAccountType());
        document.setDetectedAccType(documentDTO.getDetectedAccType());
        document.setBolNumber(documentDTO.getBolNumber());
        document.setMasterBolNumber(documentDTO.getMasterBolNumber());
        document.setPoNumber(documentDTO.getPoNumber());
        document.setQuoteNumber(documentDTO.getQuoteNumber());
        document.setTerms(documentDTO.getTerms());
        document.setShipperNumber(documentDTO.getShipperNumber());
        document.setProNumber(documentDTO.getProNumber());
        document.setRaNumber(documentDTO.getRaNumber());
        document.setEControlNumber(documentDTO.getEControlNumber());
        document.setDriverNumber(documentDTO.getDriverNumber());
        document.setRunNumber(documentDTO.getRunNumber());
        document.setCubicFeet(documentDTO.getCubicFeet());
        document.setTimeDeparted(documentDTO.getTimeDeparted());
        document.setTimeArrived(documentDTO.getTimeArrived());
        document.setDate(documentDTO.getDate());

        documentRepo.save(document);
    }

    public void updateToWaitStatus(int id) {
        var document = documentRepo.findById(id);
        document.setEntryStatus(1);

        documentRepo.save(document);
    }

    public void updateToBilledStatus(int id, String companyID) {
        var document = documentRepo.findByStoredImageTableIDAndCompanyID(id, companyID);
        document.setStatus(2);

        documentRepo.save(document);
    }
}
