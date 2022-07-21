package com.example.chargecracker.model;

public class Connector {
    private Long id;
    private String type;
    private int amperage;
    private int voltage;
    private float power;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmperage() {
        return amperage;
    }

    public void setAmperage(int amperage) {
        this.amperage = amperage;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }
}
