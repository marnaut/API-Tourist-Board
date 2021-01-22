package com.mevludin.APITouristBoard.services;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.mevludin.APITouristBoard.exceptions.EntityNotFoundException;
import com.mevludin.APITouristBoard.models.Rating;
import com.mevludin.APITouristBoard.models.Review;
import com.mevludin.APITouristBoard.models.Sight;
import com.mevludin.APITouristBoard.repositories.ReviewRepository;
import com.mevludin.APITouristBoard.repositories.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseEntity<List<Review>> getAllReviews(Long id) {
        List<Review> reviews = reviewRepository.findBySightId(id);

        return ResponseEntity.ok(reviews);
    }

    public ResponseEntity<Rating> getRating(Long sightId) {
        List<Integer> ratings = reviewRepository.findBySightId(sightId)
                .stream().map(review -> review.getRating())
                .collect(Collectors.toList());

        Double average = ratings.stream().mapToInt((x) -> x)
                .summaryStatistics().getAverage();

        Rating rating = new Rating(sightId,average);

        return ResponseEntity.ok(rating);
    }
}
