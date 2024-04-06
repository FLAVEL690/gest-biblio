package com.king.Bibliotheque.Services;

import com.king.Bibliotheque.Exceptions.RoleException;
import com.king.Bibliotheque.Exceptions.RoleNotFoundException;
import com.king.Bibliotheque.Models.Localisation;
import com.king.Bibliotheque.Repositories.LocalisationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocalisationService {
    private LocalisationRepository localisationRepository;

    public LocalisationService(LocalisationRepository localisationRepository) {
        this.localisationRepository = localisationRepository;
    }

    public void addPlace( Localisation place){
        if (localisationRepository.findByLinex(place.getLinex()) != null && localisationRepository.findByColumnx(place.getColumnx()) != null) {
            throw new RoleException("Place is already exist");
        }
        this.localisationRepository.save(place);
    }

    public List<Localisation> search(){
        return this.localisationRepository.findAll();
    }

    public Localisation getPlaceById(int id) {
        Optional<Localisation> optionalPlace = this.localisationRepository.findById(id);
        return optionalPlace.orElse(null);
    }

    public Localisation updatePlaceDetails(int id, Localisation updatedPlace) {
        Optional<Localisation> existingPlace = localisationRepository.findById(id);

        if (existingPlace.isPresent()) {
            Localisation place = existingPlace.get();

            // Update place details as needed
            place.setLinex(updatedPlace.getLinex());
            place.setColumnx(updatedPlace.getColumnx());

            // Save the updated role to the database
            return localisationRepository.save(place);
        } else {
            throw new RoleNotFoundException("Place not found");
        }
    }
    public void deletePlace(int id) {
        Optional<Localisation> place = localisationRepository.findById(id);
        if (place.isPresent()) {
            localisationRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException("Place not found");
        }
    }
}
