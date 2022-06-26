package com.javi.uned.pfgbackend.adapters.filesystem;

public enum Containers {
    SHEETS("sheets");

    private String name;

    Containers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
