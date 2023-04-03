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
  private Weinart weinart;

  @MapsId("weinrebenId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weinreben_id", nullable = false)
  private Weinrebe weinrebe;

  @Column(name = "menge", nullable = false, precision = 13, scale = 10)
  private BigDecimal menge;

  public WeinartenHasWeinrebenId getId() {
    return id;
  }

  public void setId(WeinartenHasWeinrebenId id) {
    this.id = id;
  }

  public Weinart getWeinarten() {
    return weinart;
  }

  public void setWeinarten(Weinart weinart) {
    this.weinart = weinart;
  }

  public Weinrebe getWeinreben() {
    return weinrebe;
  }

  public void setWeinreben(Weinrebe weinrebe) {
    this.weinrebe = weinrebe;
  }

  public BigDecimal getMenge() {
    return menge;
  }

  public void setMenge(BigDecimal menge) {
    this.menge = menge;
  }

}