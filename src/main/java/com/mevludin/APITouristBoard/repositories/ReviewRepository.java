package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findBySightId(Long sightId);

    Integer countBySightId(Long sightId);

    //average rating from reviews list.
    @Query(value = "SELECT AVG(e.rating) FROM Review e WHERE e.sight_id = ?1",nativeQuery = true)
    Double ratingFromReviews(Long sightId);
}
