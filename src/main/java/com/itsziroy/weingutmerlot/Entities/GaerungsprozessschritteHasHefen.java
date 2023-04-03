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
  private Gaerungsprozessschritte gaerungsprozessschritte;

  @MapsId("hefenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "hefen_id", nullable = false)
  private Hefen hefen;

  @Column(name = "menge", nullable = false)
  private Integer menge;

  public GaerungsprozessschritteHasHefenId getId() {
    return id;
  }

  public void setId(GaerungsprozessschritteHasHefenId id) {
    this.id = id;
  }

  public Gaerungsprozessschritte getGaerungsprozessschritte() {
    return gaerungsprozessschritte;
  }

  public void setGaerungsprozessschritte(Gaerungsprozessschritte gaerungsprozessschritte) {
    this.gaerungsprozessschritte = gaerungsprozessschritte;
  }

  public Hefen getHefen() {
    return hefen;
  }

  public void setHefen(Hefen hefen) {
    this.hefen = hefen;
  }

  public Integer getMenge() {
    return menge;
  }

  public void setMenge(Integer menge) {
    this.menge = menge;
  }

}