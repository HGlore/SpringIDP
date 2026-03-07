package com.idp.SpringIDP.service;

import com.idp.SpringIDP.entity.Items;
import com.idp.SpringIDP.repo.ItemsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsRepo itemsRepo;

    public List<Items> getItemsList(int id){
        return itemsRepo.findByDocumentTableID(id);
    }
}
