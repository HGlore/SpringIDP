package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Totals;
import com.idp.SpringIDP.repo.TotalsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TotalsService {

    private final TotalsRepo totalsRepo;

    public Totals getTotals(int id){
        return totalsRepo.findById(id);
    }
}
