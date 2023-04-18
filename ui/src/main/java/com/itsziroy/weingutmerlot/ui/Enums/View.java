package com.itsziroy.weingutmerlot.ui.Enums;

public enum View {
    CREATEWEIN("/views/create/wein.fxml"),
    READWEIN("/views/read/wein.fxml"),
    MAIN("/views/Main.fxml");

    private final String name;

    View(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
