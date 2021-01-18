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

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "municipality")
    private List<Sight> sights;

    public Municipality() {
    }

    public Municipality(String name, Country country) {
        this.name = name;
        this.country = country;
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

    public Country getCountry() {
        return country;
    }

    public void setState(Country country) {
        this.country = country;
    }
}
