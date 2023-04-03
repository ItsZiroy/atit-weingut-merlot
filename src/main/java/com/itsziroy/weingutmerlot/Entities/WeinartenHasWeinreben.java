package com.itsziroy.weingutmerlot.Entities;

import com.itsziroy.weingutmerlot.Entities.CompositeKeys.WeinartenHasWeinrebenId;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "weinarten_has_weinreben")
public class WeinartenHasWeinreben {
  @EmbeddedId
  private WeinartenHasWeinrebenId id;

  @MapsId("weinartenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weinarten_id", nullable = false)
  private Weinarten weinarten;

  @MapsId("weinrebenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weinreben_id", nullable = false)
  private Weinreben weinreben;

  @Column(name = "menge", nullable = false, precision = 13, scale = 10)
  private BigDecimal menge;

  public WeinartenHasWeinrebenId getId() {
    return id;
  }

  public void setId(WeinartenHasWeinrebenId id) {
    this.id = id;
  }

  public Weinarten getWeinarten() {
    return weinarten;
  }

  public void setWeinarten(Weinarten weinarten) {
    this.weinarten = weinarten;
  }

  public Weinreben getWeinreben() {
    return weinreben;
  }

  public void setWeinreben(Weinreben weinreben) {
    this.weinreben = weinreben;
  }

  public BigDecimal getMenge() {
    return menge;
  }

  public void setMenge(BigDecimal menge) {
    this.menge = menge;
  }

}