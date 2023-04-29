package com.itsziroy.weingutmerlot.ui.helper;

import com.itsziroy.weingutmerlot.backend.db.entities.Hefe;

public record HefeMenge(Hefe hefe, Double menge) {
    @Override
    public String toString() {
        return hefe.getArt() + " " + menge + "g";
    }
}
