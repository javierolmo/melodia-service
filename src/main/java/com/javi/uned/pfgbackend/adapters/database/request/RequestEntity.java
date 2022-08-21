package com.javi.uned.pfgbackend.adapters.database.request;

import com.javi.uned.pfgbackend.domain.sheet.model.Request;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "start_date_time")
    private String startDateTime;
    @Column(name = "end_date_time")
    private String endDateTime;
    @Column(name = "azf_code")
    private String azfCode;
    @Column(name = "specs", length = 5000)
    private String specs;
    @Column(name = "status")
    private String status;
    @Column(name = "sheet_id")
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

    public Request toRequest() {
        Request request = new Request();
        request.setId(id);
        request.setUserId(userId);
        request.setStartDateTime(startDateTime);
        request.setEndDateTime(endDateTime);
        request.setAzfCode(azfCode);
        request.setSpecs(specs);
        request.setStatus(status);
        request.setSheetId(sheetId);
        return request;
    }
}
