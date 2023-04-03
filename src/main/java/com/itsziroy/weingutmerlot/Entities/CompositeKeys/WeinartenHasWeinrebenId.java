package com.itsziroy.weingutmerlot.Entities.CompositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WeinartenHasWeinrebenId implements Serializable {
  private static final long serialVersionUID = 8679957690189837788L;
  @Column(name = "weinarten_id", nullable = false)
  private Integer weinartenId;

  @Column(name = "weinreben_id", nullable = false)
  private Integer weinrebenId;

  public Integer getWeinartenId() {
    return weinartenId;
  }

  public void setWeinartenId(Integer weinartenId) {
    this.weinartenId = weinartenId;
  }

  public Integer getWeinrebenId() {
    return weinrebenId;
  }

  public void setWeinrebenId(Integer weinrebenId) {
    this.weinrebenId = weinrebenId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    WeinartenHasWeinrebenId entity = (WeinartenHasWeinrebenId) o;
    return Objects.equals(this.weinartenId, entity.weinartenId) &&
            Objects.equals(this.weinrebenId, entity.weinrebenId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(weinartenId, weinrebenId);
  }

}