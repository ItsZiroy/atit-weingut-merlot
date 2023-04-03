package com.itsziroy.weingutmerlot.Entities;

import com.itsziroy.weingutmerlot.Entities.CompositeKeys.UeberpruefungenHasHefenId;
import jakarta.persistence.*;

@Entity
@Table(name = "ueberpruefungen_has_hefen")
public class UeberpruefungenHasHefen {
  @EmbeddedId
  private UeberpruefungenHasHefenId id;

  @MapsId("ueberpruefungenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "ueberpruefungen_id", nullable = false)
  private Ueberpruefung ueberpruefung;

  @MapsId("hefenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "hefen_id", nullable = false)
  private Hefe hefe;

  @Column(name = "ist", nullable = false)
  private Integer ist;

  @Column(name = "anpassung", nullable = false)
  private Integer anpassung;

  public UeberpruefungenHasHefenId getId() {
    return id;
  }

  public void setId(UeberpruefungenHasHefenId id) {
    this.id = id;
  }

  public Ueberpruefung getUeberpruefungen() {
    return ueberpruefung;
  }

  public void setUeberpruefungen(Ueberpruefung ueberpruefung) {
    this.ueberpruefung = ueberpruefung;
  }

  public Hefe getHefen() {
    return hefe;
  }

  public void setHefen(Hefe hefe) {
    this.hefe = hefe;
  }

  public Integer getIst() {
    return ist;
  }

  public void setIst(Integer ist) {
    this.ist = ist;
  }

  public Integer getAnpassung() {
    return anpassung;
  }

  public void setAnpassung(Integer anpassung) {
    this.anpassung = anpassung;
  }

}