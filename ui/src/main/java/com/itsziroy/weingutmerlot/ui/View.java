package com.itsziroy.weingutmerlot.ui;

public enum View {
    DASHBOARD("/views/Dashboard.fxml"),
    CREATE_UEBERPRUEFUNG("/views/create/ueberpruefung.fxml"),
    ERROR("/views/Error.fxml");

    View(String name) {
        this.name = name;
    }
    private final String name;

    @Override
    public String toString() {
        return name;
    }

}
