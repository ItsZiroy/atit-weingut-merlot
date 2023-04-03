package com.itsziroy.weingutmerlot.backend.Entities;

import com.itsziroy.weingutmerlot.backend.Entities.Enums.Suessegrad;
import jakarta.persistence.*;

@Entity
@Table(name = "weine")
public class Wein {
  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "weinart_id", nullable = false)
  private Weinart weinart;

  @Enumerated(EnumType.STRING)
  @Column(name = "suessegrad", nullable = false, length = 11)
  private Suessegrad suessegrad;

  @Column(name = "alkoholgehalt", nullable = false)
  private Integer alkoholgehalt;

  @Lob
  @Column(name = "beschreibung")
  private String beschreibung;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Integer getAlkoholgehalt() {
    return alkoholgehalt;
  }

  public void setAlkoholgehalt(Integer alkoholgehalt) {
    this.alkoholgehalt = alkoholgehalt;
  }

  public String getBeschreibung() {
    return beschreibung;
  }

  public void setBeschreibung(String beschreibung) {
    this.beschreibung = beschreibung;
  }

}