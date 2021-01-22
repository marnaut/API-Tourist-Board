package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Rating;
import com.mevludin.APITouristBoard.models.Review;
import com.mevludin.APITouristBoard.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/{parentId}/sights/{id}")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    //get all reviews for sight where sight id = id;
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable(name = "id") Long id){
        return reviewService.getAllReviews(id);
    }

    //save new review for sight where sight id = id
    @PostMapping
    public ResponseEntity<Review> saveReview(@PathVariable(name = "id") Long id, @RequestBody Review review){
        return reviewService.saveReview(id,review);
    }

    //Get rating for sight, where sight id = id
    @GetMapping("/rating")
    public ResponseEntity<Rating> getRating(@PathVariable(name = "id") Long sightId){
        return reviewService.getRating(sightId);
    }

}
