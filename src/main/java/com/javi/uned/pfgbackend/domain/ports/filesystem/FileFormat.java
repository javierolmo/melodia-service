package com.javi.uned.pfgbackend.domain.ports.filesystem;

public enum FileFormat {
    JSON, MUSICXML, PDF;

    public String getExtension() {
        return name().toLowerCase();
    }

}
