package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Consignee;
import com.idp.SpringIDP.repo.ConsigneeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsigneeService {

    private final ConsigneeRepo consigneeRepo;

    public Consignee getConsignee(int id){
        return consigneeRepo.findById(id);
    }
}
