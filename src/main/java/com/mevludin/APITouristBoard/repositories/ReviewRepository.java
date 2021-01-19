package com.mevludin.APITouristBoard.repositories;

import com.mevludin.APITouristBoard.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
