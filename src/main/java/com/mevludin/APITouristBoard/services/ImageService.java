package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Country;
import com.mevludin.APITouristBoard.models.Image;
import com.mevludin.APITouristBoard.models.Municipality;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.CountryRepository;
import com.mevludin.APITouristBoard.repositories.ImageDbRepository;
import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageService {
    public static String UPLOAD_DIRECTORY = "./images";

    private final ImageDbRepository imageDbRepository;

    private final SightRepository sightRepository;

    private final MunicipalityRepository municipalityRepository;

    private final CountryRepository countryRepository;

    @Autowired
    public ImageService(ImageDbRepository imageDbRepository, SightRepository sightRepository, MunicipalityRepository municipalityRepository, CountryRepository countryRepository) {
        this.imageDbRepository = imageDbRepository;
        this.sightRepository = sightRepository;
        this.municipalityRepository = municipalityRepository;
        this.countryRepository = countryRepository;
    }

    public ResponseEntity<List<Image>> uploadFile(@PathVariable Long id, @RequestParam(value="file") List<MultipartFile> multipartFiles) throws IOException {
        Sight sight = sightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id,"Sight"));

        List<Image> images = multipartListToImageList(sight,multipartFiles);

        imageDbRepository.saveAll(images);

        return  ResponseEntity.ok((images));
    }

    public List<Image> multipartListToImageList(Sight sight, List<MultipartFile> multipartFiles) {

        //create new directory by sight name, if not exist, to images directory, for storing images of sight-name
        Municipality municipality = municipalityRepository.findById(sight.getMunicipality().getId()).get();

        Country country = countryRepository.findById(municipality.getCountry().getId()).get();

        //Creating a directory to save images of each sight of municipality of country
        boolean newDirectory = new File(UPLOAD_DIRECTORY+"/"+
                country.getCountryName()+
                "/"+
                municipality.getMunicipalityName(),
                sight.getSightName())
                .mkdir();

        //Get list of images from multipartFiles
        List<Image> images = multipartFiles.stream().map(file -> {
            //Create new empty Image object
            Image image = new Image();

            //Create converted file to /image/country/municipality/sight-name/file-name
            File convertFile = new File(new StringBuilder()
                    .append(ImageService.UPLOAD_DIRECTORY)
                    .append("/")
                    .append(country.getCountryName())
                    .append("/")
                    .append(municipality.getMunicipalityName())
                    .append("/")
                    .append(sight.getSightName())
                    .append("/")
                    .toString()
                    .concat(Objects.requireNonNull(file.getOriginalFilename())));

            try {
                FileOutputStream fout = new FileOutputStream(convertFile);
                fout.write(file.getBytes());
                image.setImageName(convertFile.getName());
                image.setImagePath(convertFile.getAbsolutePath());
                image.setSight(sight);
                fout.close();
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
                return image;
        }).collect(Collectors.toList());

        return images;
    }

    public void deleteImageById(Long id) {
        Image imageToDelete = imageDbRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Image by id = "+id+" not found!"));

        File fileToDelete = new File(imageToDelete.getImagePath());
        boolean success = fileToDelete.delete();

        imageDbRepository.deleteById(id);
    }
}