package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "agragebietsektionen")
public class Agragebietsektion {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agragebiet_id", nullable = false)
  private Agragebiet agragebiet;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weinrebe_id", nullable = false)
  private Weinrebe weinrebe;

  @Column(name = "size", nullable = false)
  private Integer size;

  public Integer getId() {
    return id;
  }

  public Agragebiet getAgragebiet() {
    return agragebiet;
  }

  public void setAgragebiet(Agragebiet agragebiet) {
    this.agragebiet = agragebiet;
  }

  public Weinrebe getWeinrebe() {
    return weinrebe;
  }

  public void setWeinrebe(Weinrebe weinrebe) {
    this.weinrebe = weinrebe;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}