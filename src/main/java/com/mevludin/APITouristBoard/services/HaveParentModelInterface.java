package com.mevludin.APITouristBoard.services;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Generic interface implemented by ModelService,
 * for models that have a parent
 *
 * @param <E>
 */

public interface HaveParentModelInterface<E> {
        /**
         * Return list of all active objects where parent id = parentId
         * @param parentId = PathVariable
         * @return
         */
        ResponseEntity<List<E>> getAll(Long parentId);

        /**
         * Add new element e to database, where parent id = parentId
         * @param parentId = PathVariable
         * @param e
         */
        void save(Long parentId, E e);


        /**
         * Return object where id = id
         * @param id  = PathVariable
         * @return
         */
        ResponseEntity<E> getById(Long id);

        /**
         * Update row where id = id, and set to "e details"
         * @param id = PathVariable
         * @param e = RequestBody
         * @return
         */
        ResponseEntity<E> updateWhereId(Long id, E e);

        /**
         * Return all elements whose activities = active
         * @param parentId = PathVariable
         * @param active = RequestParam
         * @return
         */
        ResponseEntity<List<E>> getAllWhereActiveIs(Long parentId, Boolean active);

}
