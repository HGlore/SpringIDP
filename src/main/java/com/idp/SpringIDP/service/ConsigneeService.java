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

    public Consignee getConsignee(int id) {
        return consigneeRepo.findById(id);
    }

    /* void */
    public void putConsigneeData(Consignee consigneeData) {
        var consignee = consigneeRepo.findById(consigneeData.getId());
        consignee.setName(consigneeData.getName());
        consignee.setContactName(consigneeData.getContactName());
        consignee.setPhone(consigneeData.getPhone());
        consignee.setAddressLine1(consigneeData.getAddressLine1());
        consignee.setAddressLine2(consigneeData.getAddressLine2());
        consignee.setCity(consigneeData.getCity());
        consignee.setState(consigneeData.getState());
        consignee.setZipCode(consigneeData.getZipCode());

        consigneeRepo.save(consignee);
    }
}
