package com.itsziroy.weingutmerlot.backend.Entities.CompositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WeinHasZutatenId implements Serializable {
  private static final long serialVersionUID = -8023144835060793031L;
  @Column(name = "wein_id", nullable = false)
  private Integer weinId;

  @Column(name = "Zutaten_id", nullable = false)
  private Integer zutatenId;

  public Integer getWeinId() {
    return weinId;
  }

  public void setWeinId(Integer weinId) {
    this.weinId = weinId;
  }

  public Integer getZutatenId() {
    return zutatenId;
  }

  public void setZutatenId(Integer zutatenId) {
    this.zutatenId = zutatenId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    WeinHasZutatenId entity = (WeinHasZutatenId) o;
    return Objects.equals(this.zutatenId, entity.zutatenId) &&
            Objects.equals(this.weinId, entity.weinId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(zutatenId, weinId);
  }

}