package com.mevludin.APITouristBoard.models;

public class SightWithRating extends Sight{
    private Integer numOfReviews;

    private Double rating;

    public SightWithRating() {
    }

    public SightWithRating(Integer numOfReviews, Double rating) {
        this.numOfReviews = numOfReviews;
        this.rating = rating;
    }

    public SightWithRating(String sightName, String description, Double lat, Double lon, boolean activity, Importance importance, Municipality municipality, Integer numOfReviews, Double rating) {
        super(sightName, description, lat, lon, activity, importance, municipality);
        this.numOfReviews = numOfReviews;
        this.rating = rating;
    }

    public SightWithRating(Sight sight) {
        this.id = sight.id;
        this.sightName = sight.sightName;
        this.activity = sight.activity;
        this.lat = sight.lat;
        this.lon = sight.lon;
        this.importance = sight.importance;
        this.images = sight.images;
        this.municipality = sight.municipality;
        this.description = sight.description;
        this.reviews = sight.reviews;
    }

    public Integer getNumOfReviews() {
        return numOfReviews;
    }

    public void setNumOfReviews(Integer numOfReviews) {
        this.numOfReviews = numOfReviews;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
