package com.king.Bibliotheque.Controllers;

import com.king.Bibliotheque.Models.Localisation;
import com.king.Bibliotheque.Services.LocalisationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class LocalisationController {
    private LocalisationService placeService;

    public LocalisationController(LocalisationService placeService) {
        this.placeService = placeService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(path = "localisation", consumes = APPLICATION_JSON_VALUE)
    public void addPlace(@RequestBody Localisation place){
        this.placeService.addPlace(place);
    }

    @GetMapping(path = "localisation", produces = APPLICATION_JSON_VALUE)
    public List<Localisation> getPlace(){
        return this.placeService.search();
    }

    @GetMapping(path = "localisation/{id}", produces = APPLICATION_JSON_VALUE)
    public Localisation getPlaceById(@PathVariable int id){
        return this.placeService.getPlaceById(id);
    }

    @PutMapping(path = "localisation/{id}" ,consumes = APPLICATION_JSON_VALUE)
    public Localisation updatePlace(@PathVariable int id, @RequestBody Localisation updatedPlace) {
        return placeService.updatePlaceDetails(id, updatedPlace);
    }

    @DeleteMapping(path = "localisation/{id}")
    public void deletePlace(@PathVariable int id) {
        placeService.deletePlace(id);
    }

}
