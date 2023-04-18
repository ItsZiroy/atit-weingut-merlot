package com.itsziroy.weingutmerlot.ui.Enums;

public enum View {
    CREATE_WEIN("/views/create/wein.fxml"),
    READ_WEIN("/views/read/wein.fxml"),
    MAIN("/views/Main.fxml"),
    DASHBOARD("/views/Dashboard.fxml"),
    UEBERPRUEFUNG("/views/create/ueberpruefung.fxml"),
    ERROR("/views/Error.fxml");

    private final String name;

    View(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
