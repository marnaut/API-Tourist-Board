package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller koji omogućava prikaz svih općina za
 * pojedinu drzavu.
 * Drzavu pretražujemo preko njene skraćenic
 *
 * endpoints /api/v1/{countryArr}/municipalities omogućava:
 * GET dohvaćanje svih aktivnih općina u toj državi
 * POST dodavanje općine unutar drzave
 *
 * endpoints /api/v1/{countryArr}/municipalities/{id}
 * GET dohvaca odredjenu opcinu preko ID
 * PUT azurira podatke o toj opcini
 * */
@RestController
@RequestMapping("/api/v1/{countryId}/municipalities")
public class MunicipalityController {
    @Autowired
    private MunicipalityService municipalityService;

    @GetMapping
    public CollectionModel<EntityModel<Municipality>> getAllMunicipaties(@PathVariable(value = "countryId") Long countryId){
        return municipalityService.getAllMunicipalities(countryId);
    }

    @PostMapping
    public void addMunicipality(@PathVariable(value = "countryId") Long countryId,@RequestBody Municipality municipality){
        municipalityService.addMunicipality(countryId, municipality);
    }

    @GetMapping("/{id}")
    public EntityModel<Municipality> getMunicipality(@PathVariable(value = "id") Long id){
        return municipalityService.getMunicipality(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Municipality> updateMunicipality(@PathVariable(value = "id") Long id,
                                                           @RequestBody Municipality municipalityDetails){
        return municipalityService.updateMunicipality(id,municipalityDetails);
    }

}
