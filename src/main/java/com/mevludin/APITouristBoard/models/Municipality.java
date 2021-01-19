package com.mevludin.APITouristBoard.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "municipality")
    private List<Sight> sights;

    public Municipality() {
    }

    public Municipality(String name, Country country, Boolean active) {
        this.name = name;
        this.country = country;
        this.active = active;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Sight> getSights() {
        return sights;
    }

}
