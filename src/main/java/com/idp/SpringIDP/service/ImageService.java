package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.entity.Images;
import com.idp.SpringIDP.repo.DocumentRepo;
import com.idp.SpringIDP.repo.ImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepo imageRepo;
    private final DocumentRepo documentRepo;

    public ImageDTO getImagesOf(String storeDate) throws Exception {
        var images = imageRepo.findByStoredDate(storeDate);

        if (images == null || images.isEmpty()) {
            return new ImageDTO(storeDate, 0, 0, 0);
        }

        int totalQueue = (int) images.stream()
                .filter(img -> img.getStatus() == 0 && img.getAiResponse() == 1)
                .count();
        int newImages = images.size();
        int billedImages = (int) images.stream().filter(img -> img.getStatus() == 2).count();

        return new ImageDTO(storeDate, totalQueue, newImages, billedImages);
    }

    public String getImageName(int id) {
        var image = imageRepo.findById(id);
        return image.getImageName();
    }

    /* void */
    public void requestImage(String companyID) {
        var imagesList = imageRepo.findTop5ByOrderByIdAsc();

        for (Images img : imagesList) {
            img.setStatus(1); // 1 = assigned

            var document = documentRepo.findByStoredImageTableID(img.getId());
            document.setCompanyID(companyID);
            document.setStatus(1); // 1 = ongoing

            documentRepo.save(document);
            imageRepo.save(img);
        }
    }
}
