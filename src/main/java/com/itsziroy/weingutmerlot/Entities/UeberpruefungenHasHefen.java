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
  private Ueberpruefungen ueberpruefungen;

  @MapsId("hefenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "hefen_id", nullable = false)
  private Hefen hefen;

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

  public Ueberpruefungen getUeberpruefungen() {
    return ueberpruefungen;
  }

  public void setUeberpruefungen(Ueberpruefungen ueberpruefungen) {
    this.ueberpruefungen = ueberpruefungen;
  }

  public Hefen getHefen() {
    return hefen;
  }

  public void setHefen(Hefen hefen) {
    this.hefen = hefen;
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