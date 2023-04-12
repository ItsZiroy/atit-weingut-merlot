package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "weinreben")
public class Weinrebe {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToMany
  @JoinTable(
          name = "weinarten_has_weinreben",
          joinColumns = {@JoinColumn(name = "weinreben_id")},
          inverseJoinColumns = {@JoinColumn(name = "weinarten_id")}
  )
  private Set<Weinart> weinarten;

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