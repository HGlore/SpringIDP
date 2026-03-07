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

}
