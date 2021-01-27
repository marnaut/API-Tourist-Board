package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller za dodavanje, prikaz i izmjenu drzava
 * na endpointu /api/v1/countries omogucava
 * GET: dohvacanje svih raspolozivih drzava iz baze
 * POST: dodavanje nove drzave u bazu
 *
 * endpoint /api/v1/countries/{abb} omogucava
 * GET: dohvacanje drzave za odabranu skracenicu {abb}
 * PUT: izmjenu odabrane drzave*/
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    //Get all active countries
    @GetMapping
    public ResponseEntity<List<Country>> getAll(){
        return countryService.getAll();
    }
    //Save new country
    @PostMapping
    public void save(@RequestBody Country country){
        countryService.save(country);
    }
    //Get country by id
    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable("id") Long id){
        return countryService.getById(id);
    }
    //Update country where countryId = id
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateWhereId(@PathVariable(value = "id") Long id,
                                                 @RequestBody Country countryDetails){
        return countryService.updateWhereId(id,countryDetails);
    }
    //Get all active countries where activity = true,  for active
    //Get all inactive countries, where activity = false
    @GetMapping("/active")
    @ResponseBody
    public ResponseEntity<List<Country>> getActive(@RequestParam(name = "active") Boolean active){
        return countryService.getAllWhereActiveIs(active);
    }


}