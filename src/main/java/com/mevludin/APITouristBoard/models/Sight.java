package com.mevludin.APITouristBoard.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.List;


@Entity
public class Sight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    String sightName;

    @Column(nullable = false)
    String description;

    //Location lat&lon
    @Column(nullable = false)
    Double lat;

    @Column(nullable = false)
    Double lon;

    @Column(nullable = false)
    boolean activity;

    @Column(nullable = false)
    Importance importance;

    @OneToMany(mappedBy = "sight")
    List<Image> images;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "municipality_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Municipality municipality;

    @OneToMany(mappedBy = "sight")
    List<Review> reviews;



    public Sight() {
    }

    public Sight(String sightName, String description, Double lat, Double lon, boolean activity, Importance importance, Municipality municipality) {
        this.sightName = sightName;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.activity = activity;
        this.importance = importance;
        this.municipality = municipality;
    }



    public Long getId() {
        return id;
    }

    public String getSightName() {
        return sightName;
    }

    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public boolean getActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
