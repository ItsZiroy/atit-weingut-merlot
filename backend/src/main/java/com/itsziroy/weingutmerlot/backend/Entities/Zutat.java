package com.itsziroy.weingutmerlot.backend.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "zutaten")
public class Zutat {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
          name = "weine_has_zutaten",
          joinColumns = {@JoinColumn(name = "zutaten_id")},
          inverseJoinColumns = {@JoinColumn(name = "weine_id")}
  )
  private Set<Wein> weine;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}