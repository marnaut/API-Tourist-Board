package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Review;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.ReviewRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SightRepository sightRepository;

    public ResponseEntity<Review> saveReview(Long id, Review review) {
        Sight sight = sightRepository.findById(id)
                .filter(sight1 -> sight1.getActivity())
                .orElseThrow(() -> new EntityNotFoundException(id,"Sight"));

        review.setSight(sight);

        final Review finalReview = reviewRepository.save(review);

        return ResponseEntity.ok(finalReview);
    }
}
