package com.itsziroy.weingutmerlot.backend.db.entities.enums;

public enum Suessegrad {
    TROCKEN("Trocken"),
    HALBTROCKEN("Halbtrocken"),
    LIEBLICH("Lieblich"),
    SUESS("Suess"),
    FEINHERB("Feinherb");

    Suessegrad(String name) {
        this.name = name;
    }

    private final String name;

    @Override
    public String toString() {
        return name;
    }

}
