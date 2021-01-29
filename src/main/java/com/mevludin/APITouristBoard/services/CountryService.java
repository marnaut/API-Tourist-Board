package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotActiveException;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public ResponseEntity<List<Country>> getAll() {
        List<Country> countries = countryRepository.findAll();

        return ResponseEntity.ok(countries);
    }

    public ResponseEntity<Country> getById(Long id) {

        Country country = countryRepository.findByIdAndActivity(id,true).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        return ResponseEntity.ok(country);
    }

    //POST: Dodavanje nove drzave
    public void save(Country country) {
        //Creating a directory to save images of each country
        boolean newDirectory = new File(ImageService.UPLOAD_DIRECTORY,country.getCountryName()).mkdir();

        countryRepository.save(country);
    }

    //PUT: Izmjena podataka za već postojeću državu: active, countryAbbreviations, CountryName
    public ResponseEntity<Country> updateWhereId(Long id, Country updatedCountry) {
        Country country = countryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        country.setActivity(updatedCountry.getActivity());
        country.setCountryAbbreviations(updatedCountry.getCountryAbbreviations());
        country.setCountryName(updatedCountry.getCountryName());

        return ResponseEntity.ok(countryRepository.save(country));
    }

    public ResponseEntity<List<Country>> getAllWhereActiveIs(Boolean active) {
        List<Country> countries = countryRepository.findByActivity(active);

        return ResponseEntity.ok(countries);
    }
}