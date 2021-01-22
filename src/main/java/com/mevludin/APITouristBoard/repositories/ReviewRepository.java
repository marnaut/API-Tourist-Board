package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findBySightId(Long sightId);
}
