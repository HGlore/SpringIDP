package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Production;
import com.idp.SpringIDP.repo.ProductionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductionRepo productionRepo;

    public Production getProduction(int id) {
        return productionRepo.findByDocumentTableID(id);
    }

    /* void */
    public void putProductionData(Production productionData) {
        var newProd = new Production();
        newProd.setDocumentTableID(productionData.getDocumentTableID());
        newProd.setCompanyID(productionData.getCompanyID());
        newProd.setProductionDate(productionData.getProductionDate());
        newProd.setStartTime(productionData.getStartTime());
        newProd.setEndTime(productionData.getEndTime());

        if (!productionData.getComment().isEmpty()) {
            newProd.setComment(productionData.getComment());
        }

        productionRepo.save(newProd);
    }
}
