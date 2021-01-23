package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RequestMapping("/api/v1/{parentId}/municipalities")
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    @Autowired
    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    //Get all active Municipalities where countryId = parentId
    @GetMapping
    public ResponseEntity<List<Municipality>> getAllMunicipalities(@PathVariable(value = "parentId") Long parentId){
        return municipalityService.getAll(parentId);
    }

    //Save new municipality to country where countryId = parentId
    @PostMapping
    public ResponseEntity<Municipality> addMunicipality(@PathVariable(value = "parentId") Long countryId,@RequestBody Municipality municipality){
        return municipalityService.save(countryId, municipality);
    }

    //Get active municipality where municipalityId = id
    @GetMapping("/{id}")
    public ResponseEntity<Municipality> getMunicipality(@PathVariable(value = "id") Long id){
        return municipalityService.getById(id);
    }

    //Update municipality where municipalityId = id
    @PutMapping("/{id}")
    public ResponseEntity<Municipality> updateMunicipality(@PathVariable(value = "id") Long id,
                                                           @RequestBody Municipality municipalityDetails){
        return municipalityService.updateWhereId(id,municipalityDetails);
    }

    //Get active or inactive municipality, where activity = active value
    @GetMapping("/active")
    @ResponseBody
    public ResponseEntity<List<Municipality>> getIsActive(@PathVariable(value = "parentId") Long parentId, @RequestParam(name = "active") Boolean active){
        return municipalityService.getAllWhereActiveIs(parentId, active);
    }

}