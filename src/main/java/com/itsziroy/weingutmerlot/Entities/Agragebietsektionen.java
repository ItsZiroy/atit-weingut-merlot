package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "agragebietsektionen")
public class Agragebietsektionen {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agragebiet_id", nullable = false)
  private Agragebiete agragebiet;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weinrebe_id", nullable = false)
  private Weinreben weinrebe;

  @Column(name = "size", nullable = false)
  private Integer size;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Agragebiete getAgragebiet() {
    return agragebiet;
  }

  public void setAgragebiet(Agragebiete agragebiet) {
    this.agragebiet = agragebiet;
  }

  public Weinreben getWeinrebe() {
    return weinrebe;
  }

  public void setWeinrebe(Weinreben weinrebe) {
    this.weinrebe = weinrebe;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}