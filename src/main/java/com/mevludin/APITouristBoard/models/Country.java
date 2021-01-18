package com.mevludin.APITouristBoard.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 200, unique = true)
    private String countryName;

    @Column(nullable = false, unique = true)
    private String countryAbbreviations;

    @OneToMany(mappedBy = "country")
    private List<Municipality> municipalityList;


    public Country() { }

    public Country(String countryName, String countryAbbreviations) {
        this.countryName = countryName;
        this.countryAbbreviations = countryAbbreviations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Municipality> getMunicipalityList() {
        return municipalityList;
    }

}
