package com.itsziroy.weingutmerlot.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "chargen")
public class Chargen {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weintyp_id", nullable = false)
  private Weine weintyp;

  @Column(name = "jahrgang", nullable = false)
  private Integer jahrgang;

  @Column(name = "lagerungsort")
  private String lagerungsort;

  @Column(name = "menge_in_liter", nullable = false)
  private Integer mengeInLiter;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Weine getWeintyp() {
    return weintyp;
  }

  public void setWeintyp(Weine weintyp) {
    this.weintyp = weintyp;
  }

  public Integer getJahrgang() {
    return jahrgang;
  }

  public void setJahrgang(Integer jahrgang) {
    this.jahrgang = jahrgang;
  }

  public String getLagerungsort() {
    return lagerungsort;
  }

  public void setLagerungsort(String lagerungsort) {
    this.lagerungsort = lagerungsort;
  }

  public Integer getMengeInLiter() {
    return mengeInLiter;
  }

  public void setMengeInLiter(Integer mengeInLiter) {
    this.mengeInLiter = mengeInLiter;
  }

}