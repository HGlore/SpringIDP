package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Shipper;
import com.idp.SpringIDP.repo.ShipperRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepo shipperRepo;

    public Shipper getShipper(int id) {
        return shipperRepo.findById(id);
    }

    /* void */
    public void putShipperData(Shipper shipperData) {
        var shipper = shipperRepo.findById(shipperData.getId());
        shipper.setName(shipperData.getName());
        shipper.setContactName(shipperData.getContactName());
        shipper.setPhone(shipperData.getPhone());
        shipper.setAddressLine1(shipperData.getAddressLine1());
        shipper.setAddressLine2(shipperData.getAddressLine2());
        shipper.setCity(shipperData.getCity());
        shipper.setState(shipperData.getState());
        shipper.setZipCode(shipperData.getZipCode());

        shipperRepo.save(shipper);
    }
}
