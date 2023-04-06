package com.itsziroy.weingutmerlot.backend.Entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "weinarten")
public class Weinart {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "art", nullable = false, length = 11)
  private String art;

  @ManyToMany
  @JoinTable(
          name = "weinarten_has_weinreben",
          joinColumns = {@JoinColumn(name = "weinarten_id")},
          inverseJoinColumns = {@JoinColumn(name = "weinreben_id")}
  )
  private Set<Weinrebe> weinreben;

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

  public String getArt() {
    return art;
  }

  public void setArt(String art) {
    this.art = art;
  }

  @Override
  public String toString() {
    return getName() + " - " + getArt();
  }
}