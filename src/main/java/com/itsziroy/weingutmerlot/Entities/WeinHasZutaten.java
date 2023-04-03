package com.itsziroy.weingutmerlot.Entities;

import com.itsziroy.weingutmerlot.Entities.CompositeKeys.WeinHasZutatenId;
import jakarta.persistence.*;

@Entity
@Table(name = "wein_has_Zutaten")
public class WeinHasZutaten {
  @EmbeddedId
  private WeinHasZutatenId id;

  @MapsId("weinId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wein_id", nullable = false)
  private Wein wein;

  @MapsId("zutatenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "Zutaten_id", nullable = false)
  private Zutat zutat;

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

  public Wein getWein() {
    return wein;
  }

  public void setWein(Wein wein) {
    this.wein = wein;
  }

  public Zutat getZutaten() {
    return zutat;
  }

  public void setZutaten(Zutat zutat) {
    this.zutat = zutat;
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