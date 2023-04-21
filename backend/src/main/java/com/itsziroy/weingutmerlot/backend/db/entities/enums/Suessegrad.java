package com.itsziroy.weingutmerlot.backend.db.entities.enums;

public enum Suessegrad {
  TROCKEN("Trocken"),
  Trocken("Trocken"),
  HALBTROCKEN("Halbtrocken"),
  LIEBLICH("Lieblich"),
  SUESS("Suess"),
  FEINHERB("Feinherb");

  private final String name;

  Suessegrad(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

}
