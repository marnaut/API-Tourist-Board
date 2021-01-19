package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import com.mevludin.APITouristBoard.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
    @Autowired
    private CountryService countryService;


    @GetMapping
    public CollectionModel<EntityModel<Country>> getAllCountries(){
        return countryService.getAllCountries();
    }

    @PostMapping
    public void addCountry(@RequestBody Country country){
        countryService.addCountry(country);
    }

    @GetMapping("/{id}")
    public EntityModel<Country> getCountry(@PathVariable("id") Long id){
        return countryService.getCountryBy(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") Long id,
                                                 @RequestBody Country countryDetails){
        return countryService.updateCountry(id,countryDetails);
    }


}
