package com.javi.uned.pfgbackend.domain.sheet.model;

public class Sheet {

    private final Long id;
    private final String name;
    private final String date;
    private final Long ownerId;
    private final Boolean finished;
    private final String specsPath;
    private final String xmlPath;
    private final String pdfPath;

    public Sheet(Long id, String name, String date, Long ownerId, Boolean finished, String specsPath, String xmlPath, String pdfPath) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.ownerId = ownerId;
        this.finished = finished;
        this.specsPath = specsPath;
        this.xmlPath = xmlPath;
        this.pdfPath = pdfPath;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public String getSpecsPath() {
        return specsPath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }
}
