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

}
