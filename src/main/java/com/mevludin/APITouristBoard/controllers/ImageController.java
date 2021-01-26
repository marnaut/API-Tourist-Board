package com.mevludin.APITouristBoard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Image;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.ImageDbRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

@RestController
@RequestMapping("/api/v1/{municipalityId}/sights/{id}")
public class ImageController {
    public static String uploadDirectory = "C:/Users/Mevludin/Desktop/java/API-Tourist-Board/src/main/resources/image";

    @Autowired
    ImageDbRepository imageDbRepository;

    @Autowired
    SightRepository sightRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value="/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> uploadFile(@PathVariable Long id, @RequestParam(required=true, value="file") MultipartFile file) throws IOException  {
        Sight sight = sightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id,"Sight"));

        File convertFile = new File(new StringBuilder().append(uploadDirectory).append("/").toString().concat(file.getOriginalFilename()).toString());
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

}
