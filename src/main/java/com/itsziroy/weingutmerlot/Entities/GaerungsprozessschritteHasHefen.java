package com.itsziroy.weingutmerlot.Entities;

import com.itsziroy.weingutmerlot.Entities.CompositeKeys.GaerungsprozessschritteHasHefenId;
import jakarta.persistence.*;

@Entity
@Table(name = "gaerungsprozessschritte_has_hefen")
public class GaerungsprozessschritteHasHefen {
  @EmbeddedId
  private GaerungsprozessschritteHasHefenId id;

  @MapsId("gaerungsprozessschritteId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "gaerungsprozessschritte_id", nullable = false)
  private Gaerungsprozessschritt gaerungsprozessschritt;

  @MapsId("hefenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "hefen_id", nullable = false)
  private Hefe hefe;

  @Column(name = "menge", nullable = false)
  private Integer menge;

  public GaerungsprozessschritteHasHefenId getId() {
    return id;
  }

  public void setId(GaerungsprozessschritteHasHefenId id) {
    this.id = id;
  }

  public Gaerungsprozessschritt getGaerungsprozessschritte() {
    return gaerungsprozessschritt;
  }

  public void setGaerungsprozessschritte(Gaerungsprozessschritt gaerungsprozessschritt) {
    this.gaerungsprozessschritt = gaerungsprozessschritt;
  }

  public Hefe getHefen() {
    return hefe;
  }

  public void setHefen(Hefe hefe) {
    this.hefe = hefe;
  }

  public Integer getMenge() {
    return menge;
  }

  public void setMenge(Integer menge) {
    this.menge = menge;
  }

}