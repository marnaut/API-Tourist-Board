package com.mevludin.APITouristBoard.exceptions.ControllerAdvice;

import com.mevludin.APITouristBoard.exceptions.EntityNotActiveException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntityNotActiveAdvice {
    @ResponseBody
    @ExceptionHandler(EntityNotActiveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entityNotFoundHandler(EntityNotActiveException ex){
        return ex.getMessage();
    }
}
