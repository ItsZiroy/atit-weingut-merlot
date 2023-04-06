package com.itsziroy.weingutmerlot.backend.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "hefen")
public class Hefe {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "art", nullable = false)
  private String art;

  @Column(name = "temperatur", nullable = false)
  private Integer temperatur;

  @ManyToMany
  @JoinTable(
          name = "gaehrungsprozessschritte_has_hefen",
          joinColumns = {@JoinColumn(name = "hefen_id")},
          inverseJoinColumns = {@JoinColumn(name = "gaehrungsprozessschritte_id")}
  )
  private Set<Gaerungsprozessschritt> gaerungsprozessschritte;

  @ManyToMany
  @JoinTable(
          name = "ueberpruefungen_has_hefen",
          joinColumns = {@JoinColumn(name = "hefen_id")},
          inverseJoinColumns = {@JoinColumn(name = "ueberpruefungen_id")}
  )
  private Set<Ueberpruefung> ueberpruefungen;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getArt() {
    return art;
  }

  public void setArt(String art) {
    this.art = art;
  }

  public Integer getTemperatur() {
    return temperatur;
  }

  public void setTemperatur(Integer temperatur) {
    this.temperatur = temperatur;
  }

}