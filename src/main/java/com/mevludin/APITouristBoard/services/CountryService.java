package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAll() {
        List<Country> countries = countryRepository.findAll();

        return countries;
    }

    public Country getById(Long id) {

        Country country = countryRepository.findByIdAndActivity(id,true).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        return country;
    }

    //POST: Dodavanje nove drzave
    public Country save(Country country) {
        requireNonNull(country.getCountryAbbreviations());
        requireNonNull(country.getCountryName());

        //Creating a directory to save images of each country
        boolean newDirectory = new File(ImageService.UPLOAD_DIRECTORY,country.getCountryName()).mkdir();

        final Country savedCountry = countryRepository.save(country);

        return savedCountry;
    }

    //PUT: Izmjena podataka za već postojeću državu: active, countryAbbreviations, CountryName
    public Country updateWhereId(Long id, Country updatedCountry) {
        Country country = countryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        country.setActivity(updatedCountry.getActivity());
        country.setCountryAbbreviations(updatedCountry.getCountryAbbreviations());
        country.setCountryName(updatedCountry.getCountryName());

        return countryRepository.save(country);
    }

    public List<Country> getAllWhereActiveIs(Boolean active) {
        List<Country> countries = countryRepository.findByActivity(active);

        return countries;
    }
}