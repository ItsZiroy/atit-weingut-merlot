package com.itsziroy.weingutmerlot.backend.db.entities;

import com.itsziroy.weingutmerlot.backend.db.entities.enums.Suessegrad;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "weine")
public class Wein {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weinart_id", nullable = false)
  private Weinart weinart;

  @Enumerated(EnumType.STRING)
  @Column(name = "suessegrad", nullable = false, length = 11)
  private Suessegrad suessegrad;

  @Column(name = "alkoholgehalt", nullable = false)
  private Double alkoholgehalt;

  @Lob
  @Column(name = "beschreibung")
  private String beschreibung;

  @ManyToMany
  @JoinTable(
          name = "weine_has_zutaten",
          joinColumns = {@JoinColumn(name = "weine_id")},
          inverseJoinColumns = {@JoinColumn(name = "zutaten_id")}
  )
  private Set<Zutat> zutaten;

  public Integer getId() {
    return id;
  }

  public Weinart getWeinart() {
    return weinart;
  }

  public void setWeinart(Weinart weinart) {
    this.weinart = weinart;
  }

  public Suessegrad getSuessegrad() {
    return suessegrad;
  }

  public void setSuessegrad(Suessegrad suessegrad) {
    this.suessegrad = suessegrad;
  }

  public Double getAlkoholgehalt() {
    return alkoholgehalt;
  }

  public void setAlkoholgehalt(Double alkoholgehalt) {
    this.alkoholgehalt = alkoholgehalt;
  }

  public String getBeschreibung() {
    return beschreibung;
  }

  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

}