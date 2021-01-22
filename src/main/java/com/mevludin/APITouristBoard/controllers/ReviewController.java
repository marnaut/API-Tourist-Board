package com.mevludin.APITouristBoard.controllers;

import com.mevludin.APITouristBoard.models.Review;
import com.mevludin.APITouristBoard.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{parentId}/sights/{id}")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> saveReview(@PathVariable(name = "id") Long id, @RequestBody Review review){
        return reviewService.saveReview(id,review);
    }

}
