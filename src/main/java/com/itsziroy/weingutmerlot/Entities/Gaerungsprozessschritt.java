package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "gaerungsprozessschritte")
public class Gaerungsprozessschritt {
  @Id
  @Column(name = "id", nullable = false)
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

  public Integer getSchritt() {
    return schritt;
  }

  public void setSchritt(Integer schritt) {
    this.schritt = schritt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
}