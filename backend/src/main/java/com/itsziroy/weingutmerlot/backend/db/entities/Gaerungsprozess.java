package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "gaerungsprozesse")
public class Gaerungsprozess {
    @Column(name = "dauer")
    private Integer dauer;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "lagerungsbehaelter", nullable = false)
    private String lagerungsbehaelter;
    @Column(name = "temperatur", nullable = false)
    private Double temperatur;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weine_id", nullable = false)
    private Wein wein;
    @Column(name = "zuckergehalt", nullable = false)
    private Integer zuckergehalt;

    public Integer getDauer() {
        return dauer;
    }

    public void setDauer(Integer dauer) {
        this.dauer = dauer;
    }

    public Integer getId() {
        return id;
    }

    public String getLagerungsbehaelter() {
        return lagerungsbehaelter;
    }

    public void setLagerungsbehaelter(String lagerungsbehaelter) {
        this.lagerungsbehaelter = lagerungsbehaelter;
    }

    public Double getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(Double temperatur) {
        this.temperatur = temperatur;
    }

    public Wein getWein() {
        return wein;
    }

    public void setWein(Wein wein) {
        this.wein = wein;
    }

    public Integer getZuckergehalt() {
        return zuckergehalt;
    }

    public void setZuckergehalt(Integer zuckergehalt) {
        this.zuckergehalt = zuckergehalt;
    }

}