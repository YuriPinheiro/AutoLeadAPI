package com.autolead.service;

import com.autolead.domain.model.Lead;
import com.autolead.domain.model.User;
import com.autolead.domain.model.VehicleImage;
import com.autolead.domain.security.LeadAccessValidator;
import com.autolead.dto.image.ImageResponse;
import com.autolead.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageService {

    private final ImageRepository repository;
    private final CloudinaryService cloudinaryService;

    public ImageService(ImageRepository imageRepository, CloudinaryService cloudinaryService){
        this.repository = imageRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public VehicleImage get(UUID id){
        return repository.findById(id).orElseThrow();
    }

    public List<ImageResponse> listByLeadId(UUID leadId){

        return repository.findByLeadId(leadId)
                .stream()
                .map(image -> new ImageResponse(image.getId(), image.getUrl()))
                .toList();
    }

    public VehicleImage upload(MultipartFile file, Lead lead) {

        Map result = cloudinaryService.upload(file);

        String url = result.get("secure_url").toString();
        String publicId = result.get("public_id").toString();

        VehicleImage image = new VehicleImage();
        image.setUrl(url);
        image.setPublicId(publicId);
        image.setLead(lead);

        return repository.save(image);
    }

    public void deleteImage(UUID imageId, UUID leadId) {

        VehicleImage image = repository.findById(imageId)
                .orElseThrow();

        if(!image.getLead().getId().equals(leadId)){
            throw new RuntimeException("Image denied");
        }

        cloudinaryService.delete(image.getPublicId());
        repository.delete(image);
    }
}

