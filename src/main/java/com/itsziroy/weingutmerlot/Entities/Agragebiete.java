package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agragebiete")
public class Agragebiete {
  @Id
  @Column(name = "id", nullable = false)
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