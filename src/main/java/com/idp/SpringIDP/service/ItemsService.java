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

    public List<Items> getItemsList(int id) {
        return itemsRepo.findByDocumentTableID(id);
    }

    /* void */
    public void putItemsData(List<Items> itemsListData, int documentID) {

        for (Items item : itemsListData) {
            var i = itemsRepo.findById(item.getId());
            System.out.println("Item: " + i);

            if (i == null) {
                var newItem = new Items();
                newItem.setDocumentTableID(documentID);
                newItem.setPallet(item.getPallet());
                newItem.setHandlingUnit(item.getHandlingUnit());
                newItem.setPackageType(item.getPackageType());
                newItem.setPieces(item.getPieces());
                newItem.setDescription(item.getDescription());
                newItem.setWeight(item.getWeight());
                newItem.setClss(item.getClss());
                newItem.setNmfc(item.getNmfc());
                newItem.setDimension(item.getDimension());

                /* Adding new Item */
                itemsRepo.save(newItem);
            } else {
                i.setPallet(item.getPallet());
                i.setHandlingUnit(item.getHandlingUnit());
                i.setPackageType(item.getPackageType());
                i.setPieces(item.getPieces());
                i.setDescription(item.getDescription());
                i.setWeight(item.getWeight());
                i.setClss(item.getClss());
                i.setNmfc(item.getNmfc());
                i.setDimension(item.getDimension());

                /* Update Item */
                itemsRepo.save(i);
            }
        }
    }
}
