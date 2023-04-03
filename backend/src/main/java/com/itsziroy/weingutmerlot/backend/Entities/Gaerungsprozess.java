package com.itsziroy.weingutmerlot.backend.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "gaerungsprozesse")
public class Gaerungsprozess {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weine_id", nullable = false)
  private Wein wein;

  @Column(name = "zuckergehalt", nullable = false)
  private Integer zuckergehalt;

  @Column(name = "temperatur", nullable = false)
  private Integer temperatur;

  @Column(name = "dauer")
  private Integer dauer;

  @Column(name = "lagerungsbehaelter", nullable = false)
  private String lagerungsbehaelter;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Wein getWeine() {
    return wein;
  }

  public void setWeine(Wein wein) {
    this.wein = wein;
  }

  public Integer getZuckergehalt() {
    return zuckergehalt;
  }

  public void setZuckergehalt(Integer zuckergehalt) {
    this.zuckergehalt = zuckergehalt;
  }

  public Integer getTemperatur() {
    return temperatur;
  }

  public void setTemperatur(Integer temperatur) {
    this.temperatur = temperatur;
  }

  public Integer getDauer() {
    return dauer;
  }

  public void setDauer(Integer dauer) {
    this.dauer = dauer;
  }

  public String getLagerungsbehaelter() {
    return lagerungsbehaelter;
  }

  public void setLagerungsbehaelter(String lagerungsbehaelter) {
    this.lagerungsbehaelter = lagerungsbehaelter;
  }

}