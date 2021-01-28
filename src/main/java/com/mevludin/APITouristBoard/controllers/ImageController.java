package com.mevludin.APITouristBoard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Image;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.ImageDbRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/{municipalityId}/sights/{id}")
public class ImageController {

    public static String uploadDirectory = "./images";

    private final ImageDbRepository imageDbRepository;

    private final SightRepository sightRepository;

    @Autowired
    public ImageController(ImageDbRepository imageDbRepository, SightRepository sightRepository) {
        this.imageDbRepository = imageDbRepository;
        this.sightRepository = sightRepository;
    }

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadFile(@PathVariable Long id, @RequestParam(required=true, value="file") MultipartFile file) throws IOException  {
        Sight sight = sightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id,"Sight"));

        //create new directory by sight name, if not exist, to images
        boolean newDirectory = new File(uploadDirectory,sight.getSightName()).mkdir();

        File convertFile = new File(new StringBuilder().append(uploadDirectory).append("/").append(sight.getSightName()).append("/").toString().concat(file.getOriginalFilename()).toString());

        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            Image image = new Image();
            image.setImageName(convertFile.getName());
            image.setImagePath(convertFile.getAbsolutePath());
            image.setSight(sight);
        fout.close();

        return ResponseEntity.ok(imageDbRepository.save(image));
    }

    //Delete image by id of sight where sightId = id. Also delete i image file from system file.
    @DeleteMapping("/delete/{imageId}")
    public void deleteById(@PathVariable("imageId") Long id){
        Image imageToDelete = imageDbRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Image by id = "+id+" not found!"));

        File fileToDelete = new File(imageToDelete.getImagePath());
        boolean success = fileToDelete.delete();

        imageDbRepository.deleteById(id);
    }
}
