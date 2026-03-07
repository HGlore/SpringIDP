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

    public Production getProduction(int id){
        return productionRepo.findByDocumentTableID(id);
    }
}
