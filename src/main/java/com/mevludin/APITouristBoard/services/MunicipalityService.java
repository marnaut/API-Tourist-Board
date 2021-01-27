package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipalityService implements HaveParentModelInterface<Municipality>{

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Override
    public ResponseEntity<List<Municipality>> getAll(Long countryId) {
        List<Municipality> municipalities = municipalityRepository.findByCountryIdAndActivity(countryId,true);

        if(municipalities.toArray().length == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Municipality is not found or not active");

        return ResponseEntity.ok(municipalities);
    }

    @Override
    public ResponseEntity<Municipality> save(Long countryId, Municipality municipality) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(()-> new EntityNotFoundException(countryId,"Country"));

        municipality.setCountry(country);

        return ResponseEntity.ok(municipalityRepository.save(municipality));

    }

    @Override
    public ResponseEntity<Municipality> getById(Long id) {
        Municipality municipality = municipalityRepository.findByIdAndActivity(id,true)
                .orElseThrow(()-> new EntityNotFoundException(id,"Municipality"));

        return ResponseEntity.ok(municipality);
    }

    @Override
    public ResponseEntity<Municipality> updateWhereId(Long id, Municipality municipalityDetails) {
        Municipality municipality = municipalityRepository.findByIdAndActivity(id,true)
                .orElseThrow(()-> new EntityNotFoundException(id,"Municipality"));

        municipality.setMunicipalityName(municipalityDetails.getMunicipalityName());
        municipality.setActivity(municipalityDetails.getActivity());

        return ResponseEntity.ok(municipalityRepository.save(municipality));
    }

    @Override
    public ResponseEntity<List<Municipality>> getAllWhereActiveIs(Long countryId, Boolean active) {

        List<Municipality> municipalities = municipalityRepository.findByCountryIdAndActivity(countryId,active);

        return ResponseEntity.ok(municipalities);
    }
}