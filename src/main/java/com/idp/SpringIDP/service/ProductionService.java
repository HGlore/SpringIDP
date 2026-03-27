package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.ProductionDTO;
import com.idp.SpringIDP.entity.Production;
import com.idp.SpringIDP.repo.ProductionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductionRepo productionRepo;

    public Production getProduction(int id) {
        return productionRepo.findByDocumentTableID(id);
    }

    public List<Production> getProdListPerBiller(String companyID, String productionDate) {
        return productionRepo.findByCompanyIDAndProductionDate(companyID, productionDate);
    }

    public List<Production> getAllProdList(String productionDate) {
        return productionRepo.findByProductionDate(productionDate);
    }

    /* void */
    public void putProductionData(ProductionDTO productionData, String companyID, int documentID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String productionDate = LocalDate.parse(productionData.getProductionDate(), formatter).toString();

        var newProd = new Production();
        newProd.setDocumentTableID(documentID);
        newProd.setCompanyID(companyID);
        newProd.setProductionDate(productionDate);
        newProd.setStartTime(productionData.getStartTime());
        newProd.setEndTime(productionData.getEndTime());

        if (!productionData.getComment().isEmpty()) {
            newProd.setComment(productionData.getComment());
        }

        productionRepo.save(newProd);
    }

    public void updateProductionStatus(int documentID, String companyID, String productionDate, int status) {
        var production = productionRepo
                .findByDocumentTableDAndCompanyIDAndProductionDate(
                        documentID, companyID, productionDate
                );

        production.setStatus(status);
        productionRepo.save(production);
    }
}
