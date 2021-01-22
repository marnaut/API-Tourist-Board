package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotActiveException;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Importance;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SightService  {
    @Autowired
    private SightRepository sightRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;


    public List<Sight> getAll(Long parentId) {
        Municipality municipality = municipalityRepository.findById(parentId).
                orElseThrow(() -> new EntityNotFoundException(parentId,"Municipality"));

        if(!municipality.getActive())
            new EntityNotActiveException(parentId,"Municipality");

        List<Sight> sights = municipality.getSights().stream().filter(sight -> sight.getActivity()==true)
                .collect(Collectors.toList());

        return sights;
    }


    public void save(Long parentId, Sight sight) {
        Municipality municipality = municipalityRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException(parentId,"Municipality"));
        sight.setMunicipality(municipality);
        sightRepository.save(sight);
    }


    public ResponseEntity<Sight> getById(Long id) {
        Sight sight = sightRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id,"Sight"));
        if (!sight.getActivity())
            new EntityNotActiveException(id,"Sight");

        return ResponseEntity.ok(sight);
    }


    public ResponseEntity<Sight> updateWhereId(Long id, Sight sightDetails) {
        Sight sight = sightRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(id,"Sight"));
        if (!sight.getActivity())
            new EntityNotActiveException(id,"Sight");
        sight.setActivity(sightDetails.getActivity());
        sight.setSightName(sight.getSightName());
        sight.setDescription(sightDetails.getDescription());
        sight.setLat(sightDetails.getLat());
        sight.setLon(sightDetails.getLon());
        sight.setImportance(sightDetails.getImportance());

        final Sight finalSight = sightRepository.save(sight);
        return ResponseEntity.ok(finalSight);
    }


    public ResponseEntity<List<Sight>> getAllWhereActiveIs(Long parentId, Boolean active) {
        Municipality municipality = municipalityRepository.findById(parentId).
                orElseThrow(() -> new EntityNotFoundException(parentId,"Municipality"));

        if(!municipality.getActive())
            new EntityNotActiveException(parentId,"Municipality");

        List<Sight> activeSights = municipality.getSights().stream()
                .filter(sight -> sight.getActivity()==active)
                .collect(Collectors.toList());

        return ResponseEntity.ok(activeSights);
    }

    public ResponseEntity<Sight> setActivity(Long id, Boolean active) {
        Sight sight = sightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id,"Sight"));
        sight.setActivity(active);

        final Sight activityChangeOfSight = sightRepository.save(sight);

        return ResponseEntity.ok(activityChangeOfSight);
    }

    public ResponseEntity<List<Sight>> searchBy(Long parentId, Optional<Importance> importance, Optional<String> name) {
        if(importance.isPresent() && name.isPresent()){
            return null;
        } else if (importance.isPresent()){

            List<Sight> sights = sightRepository.findByMunicipalityIdAndImportance(parentId,importance);
            return ResponseEntity.ok(sights);
        } else {
            return null;
        }

    }
}
