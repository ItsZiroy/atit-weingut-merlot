package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "agragebiete")
public class Agragebiet {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "size", nullable = false)
  private Integer size;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

}