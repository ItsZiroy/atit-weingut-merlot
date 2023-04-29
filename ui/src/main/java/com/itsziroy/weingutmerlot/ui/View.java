package com.itsziroy.weingutmerlot.ui;

public enum View {
    CREATE_WEIN("/views/create/wein.fxml"),
    READ_WEIN("/views/read/wein.fxml"),
    DASHBOARD("/views/Dashboard.fxml"),
    UEBERPRUEFUNG("/views/create/ueberpruefung.fxml"),
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
