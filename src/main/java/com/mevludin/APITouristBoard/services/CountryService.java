package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotActiveException;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAll() {
        List<Country> countries = countryRepository.findAll().stream()
                .filter(country -> country.getActivity())
                .collect(Collectors.toList());
        return countries;
    }

    public ResponseEntity<Country> getById(Long id) {

        Country country = countryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        if(!country.getActivity())
            throw new EntityNotActiveException(id,"Country");

        return ResponseEntity.ok(country);
    }

    //POST: Dodavanje nove drzave
    public void save(Country country) {
        countryRepository.save(country);
    }

    //PUT: Izmjena podataka za već postojeću državu: active, countryAbbreviations, CountryName
    public ResponseEntity<Country> updateWhereId(Long id, Country updatedCountry) {
        Country country = countryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        country.setActivity(updatedCountry.getActivity());
        country.setCountryAbbreviations(updatedCountry.getCountryAbbreviations());
        country.setCountryName(updatedCountry.getCountryName());

        final Country updatedCounty = countryRepository.save(country);
        return ResponseEntity.ok(updatedCountry);
    }

    public ResponseEntity<List<Country>> getAllWhereActiveIs(Boolean active) {
        List<Country> countries = countryRepository.findAll().stream()
                .filter(country -> country.getActivity()==active)
                .collect(Collectors.toList());
        return ResponseEntity.ok(countries);
    }
}