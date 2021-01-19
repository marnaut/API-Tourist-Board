package com.mevludin.APITouristBoard.exceptions;

public class CountryNotActiveException extends RuntimeException {
    public CountryNotActiveException(Long id) {
        super("Country by id:"+id+" is not active");
    }
}
