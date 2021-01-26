package com.mevludin.APITouristBoard.models;

public class Rating {

    private Long sightId;

    private Double rating;

    private Integer numOfReviews;

    public Rating() {
    }

    public Rating(Long sightId, Double rating) {
        this.sightId = sightId;
        this.rating = rating;
    }

    public Long getSightId() {
        return sightId;
    }

    public void setSightId(Long sightId) {
        this.sightId = sightId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNumOfReviews() {
        return numOfReviews;
    }

    public void setNumOfReviews(Integer numOfReviews) {
        this.numOfReviews = numOfReviews;
    }
}
