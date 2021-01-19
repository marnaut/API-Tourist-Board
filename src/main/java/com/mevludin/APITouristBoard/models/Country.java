package com.mevludin.APITouristBoard.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 200, unique = true)
    private String countryName;

    @Column(nullable = false, unique = true)
    private String countryAbbreviations;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "country")
    private List<Municipality> municipalityList;


    public Country() { }

    public Country(String countryName, String countryAbbreviations, Boolean active) {
        this.countryName = countryName;
        this.countryAbbreviations = countryAbbreviations;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryAbbreviations() {
        return countryAbbreviations;
    }

    public void setCountryAbbreviations(String countryAbbreviations) {
        this.countryAbbreviations = countryAbbreviations;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Municipality> getMunicipalityList() {
        return municipalityList;
    }

}
