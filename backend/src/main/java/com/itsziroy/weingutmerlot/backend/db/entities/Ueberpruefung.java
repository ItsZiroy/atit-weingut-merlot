package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.entities.pivot.UeberpruefungenHasHefen;
import jakarta.persistence.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ueberpruefungen")
public class Ueberpruefung {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "chargen_id", nullable = false)
  private Charge charge;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "gaerungsprozessschritte_id", nullable = false)
  private Gaerungsprozessschritt gaerungsprozessschritt;

  @Column(name = "ist_zucker", nullable = false)
  private Integer istZucker;

  @Column(name = "ist_temperatur", nullable = false)
  private Double istTemperatur;

  @Column(name = "ist_alkohol", nullable = false)
  private Double istAlkohol;

  @Column(name = "anpassung_zucker", nullable = false)
  private Integer anpassungZucker;

  @Column(name = "anpassung_temperatur", nullable = false)
  private Double anpassungTemperatur;

  @Column(name = "naechster_schritt", nullable = false)
  private Boolean naechsterSchritt = false;

  @Column(name = "next_date")
  private Date nextDate;

  @Column(name = "datum", nullable = false)
  private Date datum;

  @OneToMany(mappedBy = "ueberpruefung")
  private Set<UeberpruefungenHasHefen> ueberpruefungenHasHefen = new LinkedHashSet<>();

  public Set<UeberpruefungenHasHefen> getHefenPivot() {
    return ueberpruefungenHasHefen;
  }

  public void setHefenPivot(Set<UeberpruefungenHasHefen> ueberpruefungenHasHefen) {
    this.ueberpruefungenHasHefen = ueberpruefungenHasHefen;
  }


  public Date getDatum() {
    return datum;
  }

  public void setDatum(Date datum) {
    this.datum = datum;
  }

  public Integer getId() {
    return id;
  }

  public Charge getCharge() {
    return charge;
  }

  public void setCharge(Charge charge) {
    this.charge = charge;
  }

  public Gaerungsprozessschritt getGaerungsprozessschritt() {
    return gaerungsprozessschritt;
  }

  public void setGaerungsprozessschritt(Gaerungsprozessschritt gaerungsprozessschritt) {
    this.gaerungsprozessschritt = gaerungsprozessschritt;
  }

  public Integer getIstZucker() {
    return istZucker;
  }

  public void setIstZucker(Integer istZucker) {
    this.istZucker = istZucker;
  }

  public Double getIstTemperatur() {
    return istTemperatur;
  }

  public void setIstTemperatur(Double istTemperatur) {
    this.istTemperatur = istTemperatur;
  }

  public Double getIstAlkohol() {
    return istAlkohol;
  }

  public void setIstAlkohol(Double istAlkohol) {
    this.istAlkohol = istAlkohol;
  }

  public Integer getAnpassungZucker() {
    return anpassungZucker;
  }

  public void setAnpassungZucker(Integer anpassungZucker) {
    this.anpassungZucker = anpassungZucker;
  }

  public Double getAnpassungTemperatur() {
    return anpassungTemperatur;
  }

  public void setAnpassungTemperatur(Double anpassungTemperatur) {
    this.anpassungTemperatur = anpassungTemperatur;
  }

  public Boolean isNaechsterSchritt() {
    return naechsterSchritt;
  }

  public void setNaechsterSchritt(Boolean naechsterSchritt) {
    this.naechsterSchritt = naechsterSchritt;
  }

  public Date getNextDate() {
    return nextDate;
  }

  public void setNextDate(Date nextDate) {
    this.nextDate = nextDate;
  }
}