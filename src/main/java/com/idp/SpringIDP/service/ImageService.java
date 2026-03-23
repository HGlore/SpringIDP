package com.idp.SpringIDP.service;

import com.idp.SpringIDP.dto.ImageDTO;
import com.idp.SpringIDP.entity.Images;
import com.idp.SpringIDP.repo.DocumentRepo;
import com.idp.SpringIDP.repo.ImageRepo;
import com.idp.SpringIDP.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepo imageRepo;
    private final DocumentRepo documentRepo;
    private final UserRepo userRepo;

    public ImageDTO getImagesStatusOf(String storeDate, String companyID) throws Exception {
        var images = imageRepo.findByStoredDate(storeDate);
        var user = userRepo.findByCompanyID(companyID);

        if (images == null || images.isEmpty()) {
            return new ImageDTO(storeDate, 0, 0, 0);
        }
        int totalQueue = 0;
        int newImages = 0;
        int billedImages = 0;

        if ("Entry".equals(user.getRole())) {
            newImages = (int) images.stream()
                    .filter(img -> user.getCompanyID().equals(img.getAssignedTo())
                            && img.getStatus() == 1 && img.getAiResponse() == 1 && img.getArchive() == 0)
                    .count();

            totalQueue = (int) images.stream()
                    .filter(img -> img.getStatus() == 1 && user.getCompanyID().equals(img.getAssignedTo())
                            && img.getAiResponse() == 1 && img.getArchive() == 0)
                    .count();

            billedImages = (int) images.stream().filter(img -> user.getCompanyID().equals(img.getAssignedTo())
                            && img.getStatus() == 2 && img.getArchive() == 0)
                    .count();

        } else {
            newImages = images.size();

            totalQueue = (int) images.stream()
                    .filter(img -> Arrays.asList(0, 1).contains(img.getStatus())
                            && img.getAiResponse() == 1)
                    .count();

            billedImages = (int) images.stream().filter(img -> img.getStatus() == 2).count();
        }

        return new ImageDTO(storeDate, totalQueue, newImages, billedImages);
    }

    public String getImageName(int id) {
        var image = imageRepo.findById(id);
        return image.getImageName();
    }

    public String getStoredDate(int id) {
        var image = imageRepo.findById(id);
        return image.getStoredDate();
    }

    public Images getImageData(int id) {
        return imageRepo.findById(id);
    }

    public List<Integer> getEntriesIDs(String companyID) {
        return imageRepo.findByAssignedToAndStatus(companyID, 1);
    }

    /* void */
    public void requestImage(String companyID) {
        var imagesList = imageRepo.findTop5ByOrderByIdAsc();

        for (Images img : imagesList) {
            img.setStatus(1); // 1 = assigned
            img.setAssignedTo(companyID);

            var document = documentRepo.findByStoredImageTableID(img.getId());
            document.setCompanyID(companyID);
            document.setStatus(1); // 1 = ongoing

            documentRepo.save(document);
            imageRepo.save(img);
        }
    }

    public void updateToBilledStatus(int id) {
        var image = imageRepo.findById(id);
        image.setStatus(2);

        imageRepo.save(image);
    }
}
