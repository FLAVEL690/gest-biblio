package com.king.Bibliotheque.Services;

import com.king.Bibliotheque.Models.Image;
import com.king.Bibliotheque.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void uploadImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setNom(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        imageRepository.save(image);
    }

//    public Optional<Image> getImageById(Long id) {
//        Image image = imageRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Image non trouvée avec l'ID : " + id));
//        return image.getData();
//    }
    public ResponseEntity<byte[]> getImageById(Long id) {
        Optional<Image> imageOptional = imageRepository.getImageById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Assurez-vous d'utiliser le bon type MIME pour votre image
                    .body(image.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Ajoutez d'autres méthodes de service selon vos besoins
}
