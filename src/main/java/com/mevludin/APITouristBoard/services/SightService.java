package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.models.Importance;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class SightService  {
    @Autowired
    private SightRepository sightRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;


    public ResponseEntity<List<Sight>> getAll(Long parentId) {
        //Find all active sights by municipalityId
        List<Sight> activeSights = sightRepository.findByMunicipalityIdAndActivity(parentId,true);

        if(activeSights.toArray().length == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sights not found, or municipality is not active");

        return ResponseEntity.ok(activeSights);
    }

    //Save new Sight, where municipalityId = parentId
    public ResponseEntity<Sight> save(Long parentId, Sight sight) {
        Municipality municipality = municipalityRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Municipality by id: "+ parentId+" not found"));

        sight.setMunicipality(municipality);

        return ResponseEntity.ok(sightRepository.save(sight));
    }

    //GET active sight, where sightId = id
    public ResponseEntity<Sight> getById(Long id) {
        Sight sight = sightRepository.findByIdAndActivity(id, true);

        if(sight == null)
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Sight by id: "+ id +" not found or not active");

        return ResponseEntity.ok(sight);
    }

    //Update Sight where sightId = id, set details of sight to sightDetails object
    public ResponseEntity<Sight> updateWhereId(Long id, Sight sightDetails) {
        Sight sight = sightRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sight by id: "+ id +" not found"));

        sight.setActivity(sightDetails.getActivity());
        sight.setSightName(sight.getSightName());
        sight.setDescription(sightDetails.getDescription());
        sight.setLat(sightDetails.getLat());
        sight.setLon(sightDetails.getLon());
        sight.setImportance(sightDetails.getImportance());


        return ResponseEntity.ok(sightRepository.save(sight));
    }

    //Get all active or inactive sight, active for active = true, inactive for active = false
    public ResponseEntity<List<Sight>> getAllWhereActiveIs(Long parentId, Boolean active) {

        List<Sight> activeSights = sightRepository.findByMunicipalityIdAndActivity(parentId, active);

        return ResponseEntity.ok(activeSights);
    }

    //Change activities for sights. Only logged in users allowed
    public ResponseEntity<Sight> setActivity(Long id, Boolean active) {
        Sight sight = sightRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Sight by id: "+ id +" not found"));

        //set activity to active value: true/false
        sight.setActivity(active);

        return ResponseEntity.ok(sightRepository.save(sight));
    }

    //Search by name or importance or name & importance
    public ResponseEntity<List<Sight>> searchBy(Long parentId, Optional<Importance> importance, Optional<String> name) {
        //find active sight by importance and name
        if(importance.isPresent() && name.isPresent()){
            List<Sight> sights = sightRepository.findByMunicipalityIdAndSightNameContainingAndImportanceAndActivity(parentId,name,importance,true);
            return ResponseEntity.ok(sights);
        } else if (importance.isPresent()){
            //find active sight by importance
            List<Sight> sights = sightRepository.findByMunicipalityIdAndImportanceAndActivity(parentId,importance,true);
            return ResponseEntity.ok(sights);
        } else if (name.isPresent()){
            //find active sight by name
            List<Sight> sights = sightRepository.findByMunicipalityIdAndSightNameContainingAndActivity(parentId,name,true);
            return ResponseEntity.ok(sights);
        } else {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"No parameters like importance or name");
        }

     }
}
