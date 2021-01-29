package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Image;
import com.mevludin.APITouristBoard.repositories.ImageDbRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import com.mevludin.APITouristBoard.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/{municipalityId}/sights/{id}")
public class ImageController {

    private final ImageDbRepository imageDbRepository;

    private final SightRepository sightRepository;

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageDbRepository imageDbRepository, SightRepository sightRepository, ImageService imageService) {
        this.imageDbRepository = imageDbRepository;
        this.sightRepository = sightRepository;
        this.imageService = imageService;
    }

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Image>> uploadFile(@PathVariable Long id, @RequestParam(required=true, value="file") List<MultipartFile> multipartFiles) throws IOException  {
        return  imageService.uploadFile(id,multipartFiles);
    }

    //Delete image by id of sight where sightId = id. Also delete i image file from system file.
    @DeleteMapping("/delete/{imageId}")
    public void deleteById(@PathVariable("imageId") Long id){
        imageService.deleteImageById(id);
    }
}
