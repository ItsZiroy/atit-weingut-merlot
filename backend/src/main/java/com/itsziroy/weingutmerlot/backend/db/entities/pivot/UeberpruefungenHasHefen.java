package com.itsziroy.weingutmerlot.backend.db.entities.pivot;

import com.itsziroy.weingutmerlot.backend.db.entities.Hefe;
import com.itsziroy.weingutmerlot.backend.db.entities.Ueberpruefung;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.composite_keys.UeberpruefungenHasHefenId;
import jakarta.persistence.*;

@Entity
@Table(name = "ueberpruefungen_has_hefen")
public class UeberpruefungenHasHefen {
    @Column(name = "anpassung", nullable = false)
    private Double anpassung;
    @MapsId("hefenId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hefen_id", nullable = false)
    private Hefe hefe;
    @EmbeddedId
    private UeberpruefungenHasHefenId id = new UeberpruefungenHasHefenId();
    @MapsId("ueberpruefungenId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ueberpruefungen_id", nullable = false)
    private Ueberpruefung ueberpruefung;

    public Double getAnpassung() {
        return anpassung;
    }

    public void setAnpassung(Double anpassung) {
        this.anpassung = anpassung;
    }

    public Hefe getHefe() {
        return hefe;
    }

    public void setHefe(Hefe hefe) {
        this.hefe = hefe;
        this.id.setHefenId(hefe.getId());
    }

    public UeberpruefungenHasHefenId getId() {
        return id;
    }

    public void setId(UeberpruefungenHasHefenId id) {
        this.id = id;
    }

    public Ueberpruefung getUeberpruefung() {
        return ueberpruefung;
    }

    public void setUeberpruefung(Ueberpruefung ueberpruefung) {
        this.ueberpruefung = ueberpruefung;
        this.id.setUeberpruefungenId(ueberpruefung.getId());
    }

}