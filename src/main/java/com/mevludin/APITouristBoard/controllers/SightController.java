package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Importance;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.services.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * A controller that implements behavior for Sight
 *
 */

@RestController
@RequestMapping("/api/v1/{parentId}/sights")
public class SightController {

    @Autowired
    private SightService sightService;

    //Get All active objects;
    @GetMapping
    public List<Sight> getAll(@PathVariable(name = "parentId") Long parentId){
        return sightService.getAll(parentId);
    }

    @PostMapping
    public void save(@PathVariable(name = "parentId") Long parentId,@RequestBody Sight sight){
        sightService.save(parentId, sight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sight> getById(@PathVariable(name = "id") Long id){
        return sightService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sight> updateWhereId(@PathVariable(name = "id") Long id, @RequestBody Sight sightDetails){
        return sightService.updateWhereId(id,sightDetails);
    }

    @GetMapping("/active")
    @ResponseBody
    public ResponseEntity<List<Sight>> getAllWhereActiveIs(@PathVariable(value = "parentId") Long parentId, @RequestParam(name = "active") Boolean active){
        return sightService.getAllWhereActiveIs(parentId,active);
    }


    /*
     * Set activity of sight true => /{id}/active?active=true
     * Set activiy of sight false => /{id}/active?active=false
     */
    @PutMapping("/{id}/active")
    public ResponseEntity<Sight> setActivityWhereId(@PathVariable(value = "id") Long id, @RequestParam(required = true, name = "active") Boolean active){
        return sightService.setActivity(id,active);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Sight>> searchBy(@PathVariable(value = "parentId") Long parentId, @RequestParam(name="importance") Optional<Importance> importance, @RequestParam Optional<String > name){

        return sightService.searchBy(parentId,importance, name);
    }

}
