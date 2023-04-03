package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ueberpruefungen")
public class Ueberpruefung {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "chargen_id", nullable = false)
  private Charge chargen;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "gaerungsprozessschritte_id", nullable = false)
  private Gaerungsprozessschritt gaerungsprozessschritt;

  @Column(name = "ist_zucker", nullable = false)
  private Integer istZucker;

  @Column(name = "ist_temperatur", nullable = false)
  private Integer istTemperatur;

  @Column(name = "ist_alkohol", nullable = false)
  private Integer istAlkohol;

  @Column(name = "anpassung_zucker", nullable = false)
  private Integer anpassungZucker;

  @Column(name = "anpassung_temperatur", nullable = false)
  private Integer anpassungTemperatur;

  @Column(name = "naechster_schritt", nullable = false)
  private Boolean naechsterSchritt = false;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Charge getChargen() {
    return chargen;
  }

  public void setChargen(Charge chargen) {
    this.chargen = chargen;
  }

  public Gaerungsprozessschritt getGaerungsprozessschritte() {
    return gaerungsprozessschritt;
  }

  public void setGaerungsprozessschritte(Gaerungsprozessschritt gaerungsprozessschritt) {
    this.gaerungsprozessschritt = gaerungsprozessschritt;
  }

  public Integer getIstZucker() {
    return istZucker;
  }

  public void setIstZucker(Integer istZucker) {
    this.istZucker = istZucker;
  }

  public Integer getIstTemperatur() {
    return istTemperatur;
  }

  public void setIstTemperatur(Integer istTemperatur) {
    this.istTemperatur = istTemperatur;
  }

  public Integer getIstAlkohol() {
    return istAlkohol;
  }

  public void setIstAlkohol(Integer istAlkohol) {
    this.istAlkohol = istAlkohol;
  }

  public Integer getAnpassungZucker() {
    return anpassungZucker;
  }

  public void setAnpassungZucker(Integer anpassungZucker) {
    this.anpassungZucker = anpassungZucker;
  }

  public Integer getAnpassungTemperatur() {
    return anpassungTemperatur;
  }

  public void setAnpassungTemperatur(Integer anpassungTemperatur) {
    this.anpassungTemperatur = anpassungTemperatur;
  }

  public Boolean getNaechsterSchritt() {
    return naechsterSchritt;
  }

  public void setNaechsterSchritt(Boolean naechsterSchritt) {
    this.naechsterSchritt = naechsterSchritt;
  }

}