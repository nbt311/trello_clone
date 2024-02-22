package com.example.trellobackend.serviceImpls.Image;

import com.example.trellobackend.models.Image;
import com.example.trellobackend.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl {
    @Autowired
    ImageRepository imageRepository;
    public String uploadImage(MultipartFile file) throws IOException {
        Image result = imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtils.compressImage(file.getBytes())).build());
        if(result != null){
            return "Saved image with name: " + file.getOriginalFilename();
        }
        return "Image not saved";
    }
    public byte[] downloadImage(String fileName){
        Optional<Image> imageFromDb = imageRepository.findByName(fileName);
        byte[] imageInbytes = ImageUtils.decompressImage(imageFromDb.get().getImage());
        return imageInbytes;
    }
}
