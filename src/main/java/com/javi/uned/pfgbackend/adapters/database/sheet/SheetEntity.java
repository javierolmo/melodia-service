package com.javi.uned.pfgbackend.adapters.database.sheet;

import javax.persistence.*;

@Entity
@Table(name = "sheets")
public class SheetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String date;
    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "finished", nullable = false)
    private Boolean finished;
    @Column(name = "specs_path")
    private String specsPath;
    @Column(name = "xml_path")
    private String xmlPath;
    @Column(name = "pdf_path")
    private String pdfPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getSpecsPath() {
        return specsPath;
    }

    public void setSpecsPath(String specsPath) {
        this.specsPath = specsPath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
