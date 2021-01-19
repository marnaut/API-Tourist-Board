package com.mevludin.APITouristBoard.exceptions;
/**
 * Exception koji se poziva kada neki zapis nije pronađen
 * */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String entity) {
        super("Could not found " + entity+ " by : "+id);
    }
}
