package com.itsziroy.weingutmerlot.backend.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "chargen")
public class Charge {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weintyp_id", nullable = false)
  private Wein wein;

  @Column(name = "jahrgang", nullable = false)
  private Integer jahrgang;

  @Column(name = "lagerungsort")
  private String lagerungsort;

  @Column(name = "menge_in_liter", nullable = false)
  private Double mengeInLiter;

  @Column(name = "istFertig", nullable = false)
  private boolean istFertig;

  public Integer getId() {
    return id;
  }

  public Wein getWein() {
    return wein;
  }

  public void setWein(Wein wein) {
    this.wein = wein;
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

  public Double getMengeInLiter() {
    return mengeInLiter;
  }

  public void setMengeInLiter(Double mengeInLiter) {
    this.mengeInLiter = mengeInLiter;
  }

  public boolean isFertig() {
    return istFertig;
  }
  public void setFertig(boolean istFertig) {
    this.istFertig = istFertig;
  }

}
