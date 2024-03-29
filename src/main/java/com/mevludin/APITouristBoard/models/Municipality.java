package com.mevludin.APITouristBoard.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String municipalityName;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Country country;

    @Column(nullable = false)
    private Boolean activity = true;

    @OneToMany(mappedBy = "municipality")
    private List<Sight> sights;

    public Municipality() {
    }

    public Municipality(String name, Country country, Boolean activity) {
        this.municipalityName = name;
        this.country = country;
        this.activity = activity;
    }

    public Long getId() {
        return id;
    }


    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Boolean getActivity() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public List<Sight> getSights() {
        return sights;
    }

}
