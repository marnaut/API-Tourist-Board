package com.mevludin.APITouristBoard.exceptions.ControllerAdvice;

import com.mevludin.APITouristBoard.exceptions.CountryNotActiveException;
import com.mevludin.APITouristBoard.exceptions.CountryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice

public class CountryNotActiveAdvice {
    @ResponseBody
    @ExceptionHandler(CountryNotActiveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String countryNotFoundHandler(CountryNotActiveException ex){
        return ex.getMessage();
    }
}
