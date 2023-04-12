package com.itsziroy.weingutmerlot.backend;

import com.itsziroy.weingutmerlot.backend.db.entities.Gaerungsprozessschritt;
import com.itsziroy.weingutmerlot.backend.db.DB;

public class Main {
  public static void main(String[] args) {
    Gaerungsprozessschritt gaerungsprozessschritt = DB.getEntityManager().find(Gaerungsprozessschritt.class, 2);
    Gaerungsprozessschritt previous = gaerungsprozessschritt.getPreviousProzessschritt();
    System.out.println(previous.getSchritt());
  }

}
