package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "weinarten")
public class Weinart {
    @Column(name = "art", nullable = false, length = 11)
    private String art;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "weinarten_has_weinreben",
            joinColumns = {@JoinColumn(name = "weinarten_id")},
            inverseJoinColumns = {@JoinColumn(name = "weinreben_id")}
    )
    private Set<Weinrebe> weinreben;

    public Integer getId() {
        return id;
    }

    public Set<Weinrebe> getWeinreben() {
        return weinreben;
    }

    public void setWeinreben(Set<Weinrebe> weinreben) {
        this.weinreben = weinreben;
    }

    @Override
    public String toString() {
        return getName() + " - " + getArt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }
}