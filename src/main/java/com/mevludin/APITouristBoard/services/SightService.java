package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SightService {
    @Autowired
    private SightRepository sightRepository;
}
