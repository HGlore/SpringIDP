package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Totals;
import com.idp.SpringIDP.repo.TotalsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TotalsService {

    private final TotalsRepo totalsRepo;

    public Totals getTotals(int id) {
        return totalsRepo.findById(id);
    }

    /* void */
    public void putTotalsData(Totals totalsData) {
        var totals = totalsRepo.findById(totalsData.getId());
        totals.setTotalPalletCnt(totalsData.getTotalPalletCnt());
        totals.setTotalHandlingUnit(totalsData.getTotalHandlingUnit());
        totals.setTotalPieces(totalsData.getTotalPieces());
        totals.setTotalWeight(totalsData.getTotalWeight());

        totalsRepo.save(totals);
    }
}
