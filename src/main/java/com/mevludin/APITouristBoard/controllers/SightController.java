package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Importance;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.services.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * A controller that implements behavior for Sight
 */

@RestController
@RequestMapping("/api/v1/{parentId}/sights")
public class SightController {

    private final SightService sightService;

    @Autowired
    public SightController(SightService sightService) {
        this.sightService = sightService;
    }

    //find all active sights, or filter by importance or filter by name, or filter by name and importance
    @GetMapping
    public ResponseEntity<List<Sight>> getAllWhere(@PathVariable(value = "parentId") Long parentId, @RequestParam(name="importance") Optional<Importance> importance, @RequestParam Optional<String > name){
        return sightService.getAllWhere(parentId,importance, name);
    }

    //Save new sight, where municipalityId = parentId
    @PostMapping
    public void save(@PathVariable(name = "parentId") Long parentId, @RequestPart Sight sight, @RequestPart(required = false) Optional<MultipartFile> file){
        sightService.save(parentId, sight, file);
    }

    //Get sight by id, where sightId = {id}
    @GetMapping("/{id}")
    public ResponseEntity<Sight> getById(@PathVariable(name = "id") Long id){
        return sightService.getById(id);
    }

    //Update sight where sightId = {id}
    @PutMapping("/{id}")
    public ResponseEntity<Sight> updateWhereId(@PathVariable(name = "id") Long id, @RequestBody Sight sightDetails){
        return sightService.updateWhereId(id,sightDetails);
    }

    //Get all active or inactive sight ?active=true for active ?active=false for inactive
    @GetMapping("/active")
    @ResponseBody
    public ResponseEntity<List<Sight>> getAllWhereWhereActiveIs(@PathVariable(value = "parentId") Long parentId, @RequestParam(name = "active") Boolean active){
        return sightService.getAllWhereActiveIs(parentId,active);
    }


    /*
     * Set activity of sight true => /{id}/active?active=true
     * Set activity of sight false => /{id}/active?active=false
     */
    @PutMapping("/{id}/active")
    public ResponseEntity<Sight> setActivityWhereId(@PathVariable(value = "id") Long id, @RequestParam(required = true, name = "active") Boolean active){
        return sightService.setActivity(id,active);
    }

}
