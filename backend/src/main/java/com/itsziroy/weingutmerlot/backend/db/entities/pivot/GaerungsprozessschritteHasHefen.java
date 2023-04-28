package com.itsziroy.weingutmerlot.backend.db.entities.pivot;

import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.entities.Hefe;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.composite_keys.GaerungsprozessschritteHasHefenId;
import jakarta.persistence.*;

@Entity
@Table(name = "gaerungsprozessschritte_has_hefen")
public class GaerungsprozessschritteHasHefen {
    @EmbeddedId
    private GaerungsprozessschritteHasHefenId id;

    @MapsId("gaerungsprozessschritteId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gaerungsprozessschritte_id", nullable = false)
    private Gaerungsprozessschritt gaerungsprozessschritt;

    @MapsId("hefenId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hefen_id", nullable = false)
    private Hefe hefe;

    @Column(name = "menge", nullable = false)
    private Double menge;

    public GaerungsprozessschritteHasHefenId getId() {
        return id;
    }

    public void setId(GaerungsprozessschritteHasHefenId id) {
        this.id = id;
    }

    public Gaerungsprozessschritt getGaerungsprozessschritt() {
        return gaerungsprozessschritt;
    }

    public void setGaerungsprozessschritt(Gaerungsprozessschritt gaerungsprozessschritt) {
        this.gaerungsprozessschritt = gaerungsprozessschritt;
    }

    public Hefe getHefe() {
        return hefe;
    }

    public void setHefe(Hefe hefe) {
        this.hefe = hefe;
    }

    public Double getMenge() {
        return menge;
    }

    public void setMenge(Double menge) {
        this.menge = menge;
    }

}