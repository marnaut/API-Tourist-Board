package com.mevludin.APITouristBoard.models;

import javax.persistence.*;

@Entity
@Table(name = "REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int mark;

    @ManyToOne
    @JoinColumn(name = "sight_id")
    private Sight sight;

    public Review() {
    }

    public Review(int mark, Sight sight) {
        this.mark = mark;
        this.sight = sight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Sight getSight() {
        return sight;
    }

    public void setSight(Sight sight) {
        this.sight = sight;
    }
}
