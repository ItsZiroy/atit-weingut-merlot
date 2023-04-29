package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.entities.pivot.GaerungsprozessschritteHasHefen;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.UeberpruefungenHasHefen;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "hefen")
public class Hefe {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "art", nullable = false)
    private String art;

    @Column(name = "temperatur", nullable = false)
    private Double temperatur;

    @OneToMany(mappedBy = "hefe")
    private Set<UeberpruefungenHasHefen> ueberpruefungenHasHefen = new LinkedHashSet<>();

    @OneToMany(mappedBy = "hefe")
    private Set<GaerungsprozessschritteHasHefen> gaerungsprozessschritteHasHefen = new LinkedHashSet<>();

    public Set<GaerungsprozessschritteHasHefen> getGaerungsprozessschrittePivot() {
        return gaerungsprozessschritteHasHefen;
    }

    public void setGaerungsprozessschrittePivot(Set<GaerungsprozessschritteHasHefen> gaerungsprozessschritteHasHefen) {
        this.gaerungsprozessschritteHasHefen = gaerungsprozessschritteHasHefen;
    }

    public Set<UeberpruefungenHasHefen> getUeberprufungenPivot() {
        return ueberpruefungenHasHefen;
    }

    public void setUeberpruefungenPivot(Set<UeberpruefungenHasHefen> ueberpruefungenHasHefen) {
        this.ueberpruefungenHasHefen = ueberpruefungenHasHefen;
    }


    public Integer getId() {
        return id;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public Double getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(Double temperatur) {
        this.temperatur = temperatur;
    }

    @Override
    public String toString() {
        return getArt() + " (" + getTemperatur() + "°C)";
    }

}