package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MunicipalityController {
    @Autowired
    private MunicipalityService municipalityService;
}
