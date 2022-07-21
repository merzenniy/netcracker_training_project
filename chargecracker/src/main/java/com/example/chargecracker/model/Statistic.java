package com.example.chargecracker.model;

import java.util.Date;

public class Statistic {
    private Long id;
    private String uri;
    private String operationType;
    private Date createdAt;
    private String businessOperationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBusinessOperationName() {
        return businessOperationName;
    }

    public void setBusinessOperationName(String businessOperationName) {
        this.businessOperationName = businessOperationName;
    }
}
