package com.itsziroy.weingutmerlot.backend.db.entities.pivot.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GaerungsprozessschritteHasHefenId implements Serializable {
    private static final long serialVersionUID = -2304847064723839100L;
    @Column(name = "gaerungsprozessschritte_id", nullable = false)
    private Integer gaerungsprozessschritteId;

    @Column(name = "hefen_id", nullable = false)
    private Integer hefenId;

    public Integer getGaerungsprozessschritteId() {
        return gaerungsprozessschritteId;
    }

    public void setGaerungsprozessschritteId(Integer gaerungsprozessschritteId) {
        this.gaerungsprozessschritteId = gaerungsprozessschritteId;
    }

    public Integer getHefenId() {
        return hefenId;
    }

    public void setHefenId(Integer hefenId) {
        this.hefenId = hefenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GaerungsprozessschritteHasHefenId entity = (GaerungsprozessschritteHasHefenId) o;
        return Objects.equals(this.gaerungsprozessschritteId, entity.gaerungsprozessschritteId) &&
                Objects.equals(this.hefenId, entity.hefenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gaerungsprozessschritteId, hefenId);
    }

}