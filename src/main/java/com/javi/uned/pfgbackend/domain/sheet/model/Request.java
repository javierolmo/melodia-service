package com.javi.uned.pfgbackend.domain.sheet.model;

import java.time.LocalDateTime;

public class Request {

    private long id;
    private long userId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String azfCode;
    private String specs;

    public Request(long userId, LocalDateTime startDateTime, LocalDateTime endDateTime, String azfCode, String specs) {
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.azfCode = azfCode;
        this.specs = specs;
    }
    public Request(long id, long userId, LocalDateTime startDateTime, LocalDateTime endDateTime, String azfCode, String specs) {
        this.id = id;
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.azfCode = azfCode;
        this.specs = specs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
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

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", userId=" + userId +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", azfCode='" + azfCode + '\'' +
                ", specs='" + specs + '\'' +
                '}';
    }
}
