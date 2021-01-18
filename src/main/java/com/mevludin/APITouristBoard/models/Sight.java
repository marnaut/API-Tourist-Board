package com.mevludin.APITouristBoard.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Sight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    //Location lat&lon
    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;

    @Column(nullable = false)
    private boolean activity;

    @Column(nullable = false)
    private Importance importance;

    @ManyToOne
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;

    @OneToMany(mappedBy = "sight")
    private List<Review> reviews;

    public Sight() {
    }

    public Sight(String name, String description, Double lat, Double lon, boolean activity, Importance importance, Municipality municipality) {
        this.name = name;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isActivity() {
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

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
