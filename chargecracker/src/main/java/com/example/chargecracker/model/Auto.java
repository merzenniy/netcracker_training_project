package com.example.chargecracker.model;

public class Auto {
    private long id;
    private long brandId;
    private String model;
    private int maxCharge;
    private long connectorId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(int maxCharge) {
        this.maxCharge = maxCharge;
    }

    public long getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(long connectorId) {
        this.connectorId = connectorId;
    }
}
