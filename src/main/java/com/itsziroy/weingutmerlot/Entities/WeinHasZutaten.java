package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wein_has_Zutaten")
public class WeinHasZutaten {
  @EmbeddedId
  private WeinHasZutatenId id;

  @MapsId("weinId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wein_id", nullable = false)
  private Weine wein;

  @MapsId("zutatenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "Zutaten_id", nullable = false)
  private Zutaten zutaten;

  @Column(name = "menge", nullable = false)
  private Integer menge;

  @Column(name = "menge_einheit", nullable = false, length = 10)
  private String mengeEinheit;

  public WeinHasZutatenId getId() {
    return id;
  }

  public void setId(WeinHasZutatenId id) {
    this.id = id;
  }

  public Weine getWein() {
    return wein;
  }

  public void setWein(Weine wein) {
    this.wein = wein;
  }

  public Zutaten getZutaten() {
    return zutaten;
  }

  public void setZutaten(Zutaten zutaten) {
    this.zutaten = zutaten;
  }

  public Integer getMenge() {
    return menge;
  }

  public void setMenge(Integer menge) {
    this.menge = menge;
  }

  public String getMengeEinheit() {
    return mengeEinheit;
  }

  public void setMengeEinheit(String mengeEinheit) {
    this.mengeEinheit = mengeEinheit;
  }

}