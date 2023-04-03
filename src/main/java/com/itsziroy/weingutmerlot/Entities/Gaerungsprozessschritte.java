package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "gaerungsprozessschritte")
public class Gaerungsprozessschritte {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "gaerungsprozess_id", nullable = false)
  private Gaerungsprozesse gaerungsprozess;

  @Column(name = "nach_zeit", nullable = false)
  private Integer nachZeit;

  @Column(name = "soll_zucker", nullable = false)
  private Integer sollZucker;

  @Column(name = "soll_temperatur", nullable = false)
  private Integer sollTemperatur;

  @Column(name = "soll_alkohol", nullable = false)
  private Integer sollAlkohol;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Gaerungsprozesse getGaerungsprozess() {
    return gaerungsprozess;
  }

  public void setGaerungsprozess(Gaerungsprozesse gaerungsprozess) {
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