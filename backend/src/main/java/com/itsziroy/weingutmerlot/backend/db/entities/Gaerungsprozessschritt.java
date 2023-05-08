package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.DB;
import com.itsziroy.weingutmerlot.backend.db.entities.pivot.GaerungsprozessschritteHasHefen;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "gaerungsprozessschritte")
public class Gaerungsprozessschritt {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gaerungsprozess_id", nullable = false)
    private Gaerungsprozess gaerungsprozess;
    @OneToMany(mappedBy = "gaerungsprozessschritt")
    private Set<GaerungsprozessschritteHasHefen> gaerungsprozessschritteHasHefen = new LinkedHashSet<>();
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nach_zeit", nullable = false)
    private Integer nachZeit;
    /**
     * Gibt die Rangfolge der Prozessschritte an
     */
    @Column(name = "schritt", nullable = false)
    private Integer schritt;
    @Column(name = "soll_alkohol", nullable = false)
    private Integer sollAlkohol;
    @Column(name = "soll_temperatur", nullable = false)
    private Integer sollTemperatur;
    @Column(name = "soll_zucker", nullable = false)
    private Integer sollZucker;

    public Gaerungsprozess getGaerungsprozess() {
        return gaerungsprozess;
    }

    public void setGaerungsprozess(Gaerungsprozess gaerungsprozess) {
        this.gaerungsprozess = gaerungsprozess;
    }

    public Set<GaerungsprozessschritteHasHefen> getHefenPivot() {
        return gaerungsprozessschritteHasHefen;
    }

    public void setHefenPivot(Set<GaerungsprozessschritteHasHefen> gaerungsprozessschritteHasHefen) {
        this.gaerungsprozessschritteHasHefen = gaerungsprozessschritteHasHefen;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNachZeit() {
        return nachZeit;
    }

    public void setNachZeit(Integer nachZeit) {
        this.nachZeit = nachZeit;
    }

    /**
     * Gets the next Prozessschritt
     * @return Gärungsprozessschritt
     */
    public Gaerungsprozessschritt getNextProzessschritt() {
        return this.getSiblingProzessschrittByNumber(this.getSchritt() + 1);
    }

    /**
     * Finds a sibling Processschritt by the step identifier
     * @param schritt step
     * @return Gärungsprozessschritt
     */
    public Gaerungsprozessschritt getSiblingProzessschrittByNumber(int schritt) {
        TypedQuery<Gaerungsprozessschritt> query = DB.getEntityManager().createQuery("select p from Gaerungsprozessschritt p " +
                "where p.schritt = :schritt and p.gaerungsprozess.id = :gaerungsprozess_id", Gaerungsprozessschritt.class);

        query.setParameter("schritt", schritt);
        query.setParameter("gaerungsprozess_id", this.gaerungsprozess.getId());

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getSchritt() {
        return schritt;
    }

    public void setSchritt(Integer schritt) {
        this.schritt = schritt;
    }

    /**
     * Gets the previous Gärungsprozessschritt
     * @return Gärungsprozessschritt
     */
    public Gaerungsprozessschritt getPreviousProzessschritt() {
        return this.getSiblingProzessschrittByNumber(this.getSchritt() - 1);
    }

    public Integer getSollAlkohol() {
        return sollAlkohol;
    }

    public void setSollAlkohol(Integer sollAlkohol) {
        this.sollAlkohol = sollAlkohol;
    }

    public Integer getSollTemperatur() {
        return sollTemperatur;
    }

    public void setSollTemperatur(Integer sollTemperatur) {
        this.sollTemperatur = sollTemperatur;
    }

    public Integer getSollZucker() {
        return sollZucker;
    }

    public void setSollZucker(Integer sollZucker) {
        this.sollZucker = sollZucker;
    }
}