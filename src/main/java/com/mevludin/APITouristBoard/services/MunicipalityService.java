package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        Country country = countryRepository.findById(countryId)
                .filter(country1 -> country1.getActive())
                .orElseThrow(()-> new EntityNotFoundException(countryId,"Country"));

        List<Municipality> municipalities = country.getMunicipalityList().stream()
                .filter(municipality -> municipality.getActive())
                .collect(Collectors.toList());

        return ResponseEntity.ok(municipalities);
    }

    @Override
    public void save(Long countryId, Municipality municipality) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(()-> new EntityNotFoundException(countryId,"Country"));

        municipality.setCountry(country);
        municipalityRepository.save(municipality);
    }

    @Override
    public ResponseEntity<Municipality> getById(Long id) {
        Municipality municipality = municipalityRepository.findById(id)
                .filter(municipality1 -> municipality1.getActive())
                .orElseThrow(() -> new EntityNotFoundException(id,"Municipality"));

        return ResponseEntity.ok(municipality);
    }

    @Override
    public ResponseEntity<Municipality> updateWhereId(Long id, Municipality municipalityDetails) {
        Municipality municipality = municipalityRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id,"Municipality"));

        municipality.setMunicipalityName(municipalityDetails.getMunicipalityName());
        municipality.setActive(municipalityDetails.getActive());

        final Municipality updateMunicipality = municipalityRepository.save(municipality);
        return ResponseEntity.ok(updateMunicipality);
    }

    @Override
    public ResponseEntity<List<Municipality>> getAllWhereActiveIs(Long countryId, Boolean active) {
        Country country = countryRepository.findById(countryId).filter(country1 -> country1.getActive()==true)
                .filter(country1 -> country1.getActive())
                .orElseThrow(() -> new EntityNotFoundException(countryId,"Country"));

        List<Municipality> municipalities = country.getMunicipalityList().stream()
                .filter(municipality -> municipality.getActive()==active)
                .collect(Collectors.toList());
        return ResponseEntity.ok(municipalities);
    }
}