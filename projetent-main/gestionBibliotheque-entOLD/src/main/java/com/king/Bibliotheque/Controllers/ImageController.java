package com.king.Bibliotheque.Controllers;

import com.king.Bibliotheque.Models.Image;
import com.king.Bibliotheque.Services.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            imageService.uploadImage(file);
            return ResponseEntity.ok("Image uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }
//    @GetMapping("imageOLD/{id}")
//    public ResponseEntity<Optional<Image>> getImageById(@PathVariable Long id) {
//        ResponseEntity<byte[]> imageData = imageService.getImageById(id);
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData);
//    }

    @GetMapping("image/{id}")
    public void getImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        ResponseEntity<byte[]> imageOptional = imageService.getImageById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            response.setContentType("image/jpg"); // Assurez-vous d'utiliser le bon type MIME pour votre image
            response.getOutputStream().write(image.getData());
            response.getOutputStream().close();
        } else {
            // Gérer le cas où l'image n'est pas trouvée
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // Ajoutez d'autres méthodes de contrôleur selon vos besoins
}
