package com.itsziroy.weingutmerlot.backend.db.entities.pivot.composite_keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UeberpruefungenHasHefenId implements Serializable {
    private static final long serialVersionUID = 6567144087401199505L;
    @Column(name = "hefen_id", nullable = false)
    private Integer hefenId;
    @Column(name = "ueberpruefungen_id", nullable = false)
    private Integer ueberpruefungenId;

    public Integer getHefenId() {
        return hefenId;
    }

    public void setHefenId(Integer hefenId) {
        this.hefenId = hefenId;
    }

    public Integer getUeberpruefungenId() {
        return ueberpruefungenId;
    }

    public void setUeberpruefungenId(Integer ueberpruefungenId) {
        this.ueberpruefungenId = ueberpruefungenId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hefenId, ueberpruefungenId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UeberpruefungenHasHefenId entity = (UeberpruefungenHasHefenId) o;
        return Objects.equals(this.hefenId, entity.hefenId) &&
                Objects.equals(this.ueberpruefungenId, entity.ueberpruefungenId);
    }

}