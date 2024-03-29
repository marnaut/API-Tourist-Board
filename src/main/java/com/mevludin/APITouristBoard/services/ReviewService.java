package com.mevludin.APITouristBoard.services;

import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Review;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.ReviewRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;


    private final SightRepository sightRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, SightRepository sightRepository ){
        this.reviewRepository = reviewRepository;
        this.sightRepository = sightRepository;
    }

    public Review saveReview(Long id, Review review) {

        if(review.getRating()>0 && review.getRating()<6) {
            Sight sight = sightRepository.findById(id)
                    .filter(sight1 -> sight1.getActivity())
                    .orElseThrow(() -> new EntityNotFoundException(id, "Sight"));

            review.setSight(sight);

            final Review finalReview = reviewRepository.save(review);

            return finalReview;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating must be between 1 and 5");
        }
    }

    public List<Review> getAllReviews(Long id) {
        List<Review> reviews = reviewRepository.findBySightId(id);

        return reviews;
    }

}
