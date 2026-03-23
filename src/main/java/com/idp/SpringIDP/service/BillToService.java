package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.BillTo;
import com.idp.SpringIDP.repo.BillToRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillToService {

    private final BillToRepo billToRepo;

    public BillTo getBillTo(int id) {
        return billToRepo.findById(id);
    }

    /* void */
    public void putBillToData(BillTo billToData) {
        var billTo = billToRepo.findById(billToData.getId());
        billTo.setName(billToData.getName());
        billTo.setContactName(billToData.getContactName());
        billTo.setPhone(billToData.getPhone());
        billTo.setAddressLine1(billToData.getAddressLine1());
        billTo.setAddressLine2(billToData.getAddressLine2());
        billTo.setCity(billToData.getCity());
        billTo.setState(billToData.getState());
        billTo.setZipCode(billToData.getZipCode());

        billToRepo.save(billTo);
    }
}
