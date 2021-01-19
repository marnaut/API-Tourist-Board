package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.services.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SightController {
    @Autowired
    private SightService sightService;
}
