package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.ProductionDTO;
import com.idp.SpringIDP.entity.Production;
import com.idp.SpringIDP.repo.ProductionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

    public List<Production> getAllProdList(String storeDate) {
        return productionRepo.findByProductionDate(storeDate);
    }

    /* void */
    public void putProductionData(ProductionDTO productionData) {
        var newProd = new Production();
        newProd.setDocumentTableID(productionData.getDocumentTableID());
        newProd.setCompanyID(productionData.getCompanyID());
        newProd.setProductionDate(productionData.getProductionDate());
        newProd.setStartTime(LocalTime.parse(productionData.getStartTime()));
        newProd.setEndTime(LocalTime.parse(productionData.getEndTime()));

        if (!productionData.getComment().isEmpty()) {
            newProd.setComment(productionData.getComment());
        }

        productionRepo.save(newProd);
    }

    public void updateProductionStatus(){

    }
}
