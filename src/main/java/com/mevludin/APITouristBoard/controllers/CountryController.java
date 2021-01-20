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
    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<Country> getAll(){
        return countryService.getAll();
    }

    @PostMapping
    public void save(@RequestBody Country country){
        countryService.save(country);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(@PathVariable("id") Long id){
        return countryService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateWhereId(@PathVariable(value = "id") Long id,
                                                 @RequestBody Country countryDetails){
        return countryService.updateWhereId(id,countryDetails);
    }

    @GetMapping("/active")
    @ResponseBody
    public ResponseEntity<List<Country>> getActive(@RequestParam(name = "active") Boolean active){
        return countryService.getAllWhereActiveIs(active);
    }


}