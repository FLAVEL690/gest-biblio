package com.king.Bibliotheque.Repositories;

import com.king.Bibliotheque.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> getImageById(Long id);
    // Vous pouvez ajouter des méthodes supplémentaires spécifiques à la gestion des images si nécessaire
}
