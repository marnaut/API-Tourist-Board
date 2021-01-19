package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityService {

    @Autowired
    private MunicipalityRepository municipalityRepository;
}
