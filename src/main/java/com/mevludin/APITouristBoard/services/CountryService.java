package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;


}
