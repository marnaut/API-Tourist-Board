package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.controllers.CountryController;
import com.mevludin.APITouristBoard.exceptions.EntityNotActiveException;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    //GET: sve države, sa linkovima na sve države, i svaku pojedinačnu.
    public CollectionModel<EntityModel<Country>> getAllCountries() {
        List<EntityModel<Country>> countries = countryRepository.findAll().stream()
                .filter(country -> country.getActive())
                .map(country -> EntityModel.of(country,
                        linkTo(methodOn(CountryController.class).getCountry(country.getId())).withSelfRel(),
                        linkTo(methodOn(CountryController.class).getAllCountries()).withRel("countries")))
                .collect(Collectors.toList());
        return CollectionModel.of(countries,
                linkTo(methodOn(CountryController.class).getAllCountries()).withSelfRel());
    }

    /*
        GET: Država pomoću ID, uključujući linkove na sve države i na samu sebe.
        Prikazuje samo aktivne države, dok za neaktivne šalje poruku.
     */

    public EntityModel<Country> getCountryBy(Long id) {

        Country country = countryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        if(!country.getActive())
            throw new EntityNotActiveException(id,"Country");

        return EntityModel.of(country,
                linkTo(methodOn(CountryController.class).getCountry(id)).withSelfRel(),
                linkTo(methodOn(CountryController.class).getAllCountries()).withRel("countries"));
    }

    //POST: Dodavanje nove drzave
    public void addCountry(Country country) {
        countryRepository.save(country);
    }

    //PUT: Izmjena podataka za već postojeću državu: active, countryAbbreviations, CountryName
    public ResponseEntity<Country> updateCountry(Long id, Country updatedCountry) {
        Country country = countryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Country"));

        country.setActive(updatedCountry.getActive());
        country.setCountryAbbreviations(updatedCountry.getCountryAbbreviations());
        country.setCountryName(updatedCountry.getCountryName());

        final Country updatedCounty = countryRepository.save(country);
        return ResponseEntity.ok(updatedCountry);
    }
}
