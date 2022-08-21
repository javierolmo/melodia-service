package com.javi.uned.pfgbackend.domain.sheet.model;

import com.javi.uned.pfgbackend.adapters.database.request.RequestEntity;
import java.io.Serializable;

public class Request implements Serializable {

    private Long id;
    private Long userId;
    private String startDateTime;
    private String endDateTime;
    private String azfCode;
    private String specs;
    private String status;
    private Long sheetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getAzfCode() {
        return azfCode;
    }

    public void setAzfCode(String azfCode) {
        this.azfCode = azfCode;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSheetId() {
        return sheetId;
    }

    public void setSheetId(Long sheetId) {
        this.sheetId = sheetId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", userId=" + userId +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", azfCode='" + azfCode + '\'' +
                ", specs='" + specs + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * Converts a Request to a RequestEntity
     *
     * @return
     */
    public RequestEntity toEntity() {
        RequestEntity entity = new RequestEntity();
        entity.setId(id);
        entity.setUserId(userId);
        entity.setStartDateTime(startDateTime);
        entity.setEndDateTime(endDateTime);
        entity.setAzfCode(azfCode);
        entity.setSpecs(specs);
        entity.setStatus(status);
        entity.setSheetId(sheetId);
        return entity;
    }
}
