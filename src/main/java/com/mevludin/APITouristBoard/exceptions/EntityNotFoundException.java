package com.mevludin.APITouristBoard.exceptions;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(Long id, String entity) {
        super("Could not found " + entity+ " by : "+id);
    }
}
