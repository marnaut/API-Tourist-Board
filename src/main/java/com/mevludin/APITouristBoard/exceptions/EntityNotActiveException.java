package com.mevludin.APITouristBoard.exceptions;
/**
 * Exception koji se poziva kada neki zapis nije aktivan
 * */

public class EntityNotActiveException extends RuntimeException {
    public EntityNotActiveException(Long id, String entity) {
        super(entity +" by id:"+id+" is not active");
    }
}
