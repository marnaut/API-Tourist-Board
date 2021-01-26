package com.mevludin.APITouristBoard.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mevludin.APITouristBoard.controllers.ImageController;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Image;
import com.mevludin.APITouristBoard.models.Importance;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.ImageDbRepository;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import com.mevludin.APITouristBoard.repositories.ReviewRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class SightService  {
    public static String uploadDirectory = "C:/Users/Mevludin/Desktop/java/API-Tourist-Board/src/main/resources/image";


    private final SightRepository sightRepository;

    private final MunicipalityRepository municipalityRepository;

    private final ReviewRepository reviewRepository;

    private ImageDbRepository imageDbRepository;

    @Autowired
    public SightService(SightRepository sightRepository, MunicipalityRepository municipalityRepository, ReviewRepository reviewRepository, ImageDbRepository imageDbRepository) {
        this.sightRepository = sightRepository;
        this.municipalityRepository = municipalityRepository;
        this.reviewRepository = reviewRepository;
        this.imageDbRepository = imageDbRepository;
    }

    //find all active sights, or filter by importance or filter by name, or filter by name and importance
    public ResponseEntity<List<Sight>> getAllWhere(Long parentId, Optional<Importance> importance, Optional<String> name) {
        List<Sight> sights;
        //find active sight by importance and name
        if(importance.isPresent() && name.isPresent()){
             sights = sightRepository.findByMunicipalityIdAndSightNameContainingAndImportanceAndActivity(parentId,name,importance,true);
        } else if (importance.isPresent()){
            //find active sight by importance
            sights = sightRepository.findByMunicipalityIdAndImportanceAndActivity(parentId,importance,true);
        } else if (name.isPresent()){
            //find active sight by name
            sights = sightRepository.findByMunicipalityIdAndSightNameContainingAndActivity(parentId,name,true);
        } else {
            //Find all active sights by municipalityId
            sights = sightRepository.findByMunicipalityIdAndActivity(parentId,true);

            if(sights.toArray().length == 0)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sights not found, or municipality is not active");
        }

        return ResponseEntity.ok(sights);

    }

    //Save new Sight, where municipalityId = parentId
    public ResponseEntity<Sight> save(Long parentId, Sight sight, MultipartFile file) {

        Municipality municipality = municipalityRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Municipality by id: "+ parentId+" not found"));

        sight.setMunicipality(municipality);
        System.out.println(sight.getSightName()+" " + sight.getActivity()+ " "  + sight.getLat()+ " " + file.getName());
        sightRepository.save(sight);
        try {
            Sight newSight = sightRepository.findById(sight.getId()).orElseThrow(() -> new EntityNotFoundException(sight.getId(),"Sight"));

            File convertFile = new File(new StringBuilder().append(uploadDirectory).append("/").toString().concat(file.getOriginalFilename()).toString());
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            Image image = new Image();
            image.setImageName(convertFile.getName());
            image.setImagePath(convertFile.getAbsolutePath());
            image.setSight(newSight);
            fout.close();
            imageDbRepository.save(image);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok(sight);

    }

    //GET active sight, where sightId = id
    public ResponseEntity<Sight> getById(Long id) {

        Sight sight = sightRepository.findByIdAndActivity(id, true);

        if(sight == null)
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Sight by id: "+ id +" not found or not active");

        //get number of reviews
        Integer numOfReviews = reviewRepository.countBySightId(id);

        //get rating
        Double rating = reviewRepository.ratingFromReviews(id);

        sight.setRating(rating);
        sight.setNumOfReviews(numOfReviews);

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

}
