package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hefen")
public class Hefen {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "art", nullable = false)
  private String art;

  @Column(name = "temperatur", nullable = false)
  private Integer temperatur;

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