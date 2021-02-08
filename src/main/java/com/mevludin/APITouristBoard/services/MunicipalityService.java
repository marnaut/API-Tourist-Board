package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.List;

@Service
public class MunicipalityService{

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private MunicipalityRepository municipalityRepository;

    public List<Municipality> getAll(Long countryId) {
        List<Municipality> municipalities = municipalityRepository.findByCountryIdAndActivity(countryId,true);

        if(municipalities.toArray().length == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Municipality is not found or not active");

        return municipalities;
    }

    public Municipality save(Long countryId, Municipality municipality) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(()-> new EntityNotFoundException(countryId,"Country"));

        municipality.setCountry(country);

        //Creating a directory to save images of each municipality
        boolean newDirectory = new File(ImageService.UPLOAD_DIRECTORY+"/"+country.getCountryName()
                ,municipality.getMunicipalityName()).mkdir();

        final Municipality savedMunicipality = municipalityRepository.save(municipality);

        return savedMunicipality;
    }

    public Municipality getById(Long id) {
        Municipality municipality = municipalityRepository.findByIdAndActivity(id,true)
                .orElseThrow(()-> new EntityNotFoundException(id,"Municipality"));

        return municipality;
    }

    public Municipality updateWhereId(Long id, Municipality municipalityDetails) {
        Municipality municipality = municipalityRepository.findByIdAndActivity(id,true)
                .orElseThrow(()-> new EntityNotFoundException(id,"Municipality"));

        municipality.setMunicipalityName(municipalityDetails.getMunicipalityName());
        municipality.setActivity(municipalityDetails.getActivity());

        return municipalityRepository.save(municipality);
    }

    public List<Municipality> getAllWhereActiveIs(Long countryId, Boolean active) {

        List<Municipality> municipalities = municipalityRepository.findByCountryIdAndActivity(countryId,active);

        return municipalities;
    }
}