package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.DB;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "gaerungsprozessschritte")
public class Gaerungsprozessschritt {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "gaerungsprozess_id", nullable = false)
  private Gaerungsprozess gaerungsprozess;

  @Column(name = "nach_zeit", nullable = false)
  private Integer nachZeit;

  @Column(name = "soll_zucker", nullable = false)
  private Integer sollZucker;

  @Column(name = "soll_temperatur", nullable = false)
  private Integer sollTemperatur;

  @Column(name = "soll_alkohol", nullable = false)
  private Integer sollAlkohol;

  /**
   * Gibt die Rangfolge der Prozessschritte an
   */
  @Column(name = "schritt", nullable = false)
  private Integer schritt;

  @ManyToMany
  @JoinTable(
          name = "gaerungsprozessschritte_has_hefen",
          joinColumns = {@JoinColumn(name = "gaerungsprozessschritte_id")},
          inverseJoinColumns = {@JoinColumn(name = "hefen_id")}
  )
  private Set<Hefe> hefen;

  public Integer getSchritt() {
    return schritt;
  }

  public void setSchritt(Integer schritt) {
    this.schritt = schritt;
  }

  public Integer getId() {
    return id;
  }

  public Gaerungsprozess getGaerungsprozess() {
    return gaerungsprozess;
  }

  public void setGaerungsprozess(Gaerungsprozess gaerungsprozess) {
    this.gaerungsprozess = gaerungsprozess;
  }

  public Integer getNachZeit() {
    return nachZeit;
  }

  public void setNachZeit(Integer nachZeit) {
    this.nachZeit = nachZeit;
  }

  public Integer getSollZucker() {
    return sollZucker;
  }

  public void setSollZucker(Integer sollZucker) {
    this.sollZucker = sollZucker;
  }

  public Integer getSollTemperatur() {
    return sollTemperatur;
  }

  public void setSollTemperatur(Integer sollTemperatur) {
    this.sollTemperatur = sollTemperatur;
  }

  public Integer getSollAlkohol() {
    return sollAlkohol;
  }

  public void setSollAlkohol(Integer sollAlkohol) {
    this.sollAlkohol = sollAlkohol;
  }

  public Gaerungsprozessschritt getPreviousProzessschritt() {
    return this.getSiblingProzessschrittByNumber(this.getSchritt() - 1);
  }
  public Gaerungsprozessschritt getNextProzessschritt() {
    return this.getSiblingProzessschrittByNumber(this.getSchritt() + 1);
  }
  public Gaerungsprozessschritt getSiblingProzessschrittByNumber(int schritt) {
    TypedQuery<Gaerungsprozessschritt> query = DB.getEntityManager().createQuery("select p from Gaerungsprozessschritt p " +
            "where p.schritt = :schritt and p.gaerungsprozess.id = :gaerungsprozess_id", Gaerungsprozessschritt.class);

    query.setParameter("schritt",schritt);
    query.setParameter("gaerungsprozess_id", this.gaerungsprozess.getId());

    try {
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }
}