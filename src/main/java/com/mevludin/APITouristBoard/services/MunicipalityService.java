package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.controllers.MunicipalityController;
import com.mevludin.APITouristBoard.exceptions.EntityNotActiveException;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
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
public class MunicipalityService {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private MunicipalityRepository municipalityRepository;

    public CollectionModel<EntityModel<Municipality>> getAllMunicipalities(Long countryId) {
        Country country = countryRepository.findById(countryId).orElseThrow(()-> new EntityNotFoundException(countryId,"Country"));
        List<EntityModel<Municipality>> municipalities = country.getMunicipalityList().stream()
                .filter(municipality -> municipality.getActive())
                .map(municipality -> EntityModel.of(municipality,
                        linkTo(methodOn(MunicipalityController.class).getMunicipality(municipality.getId())).withSelfRel(),
                        linkTo(methodOn(MunicipalityController.class).getAllMunicipaties(municipality.getCountry().getId())).withRel("municipaties")))
                .collect(Collectors.toList());

        return CollectionModel.of(municipalities);
    }

    public void addMunicipality(Long countryId, Municipality municipality) {
        Country country = countryRepository.findById(countryId).orElseThrow(()-> new EntityNotFoundException(countryId,"Country"));
        municipality.setCountry(country);
        municipalityRepository.save(municipality);
    }

    public EntityModel<Municipality> getMunicipality(Long id) {
        Municipality municipality = municipalityRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(id,"Municipality"));

        if(!municipality.getActive())
            throw new EntityNotActiveException(id,"Municipality");

        return EntityModel.of(municipality,
                linkTo(methodOn(MunicipalityController.class).getMunicipality(municipality.getId())).withSelfRel(),
                linkTo(methodOn(MunicipalityController.class).getAllMunicipaties(municipality.getCountry().getId())).withRel("municipalities"));
    }

    public ResponseEntity<Municipality> updateMunicipality(Long id, Municipality municipalityDetails) {
        Municipality municipality = municipalityRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id,"Municipality"));

        municipality.setName(municipalityDetails.getName());
        municipality.setActive(municipalityDetails.getActive());

        final Municipality updateMunicipality = municipalityRepository.save(municipality);
        return ResponseEntity.ok(updateMunicipality);
    }
}
