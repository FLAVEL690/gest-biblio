package com.king.Bibliotheque.Repositories;

import com.king.Bibliotheque.Models.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalisationRepository extends JpaRepository<Localisation, Integer> {
    Localisation findByLinex(String line);
    Localisation findByColumnx(String column);
}
