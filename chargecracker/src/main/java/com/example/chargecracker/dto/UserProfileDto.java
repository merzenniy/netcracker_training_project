package com.example.chargecracker.dto;

public class UserProfileDto {
    private Long userId;
    private String userLastName;
    private String userFirstName;
    private String userMidName;
    private String userMail;

    private Long autoId;
    private String autoModel;
    private int autoMaxCharge;

    private String brandName;

    private String connectorType;
    private int connectorAmperage;
    private int connectorVoltage;
    private float connectorPower;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserMidName() {
        return userMidName;
    }

    public void setUserMidName(String userMidName) {
        this.userMidName = userMidName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public String getAutoModel() {
        return autoModel;
    }

    public void setAutoModel(String autoModel) {
        this.autoModel = autoModel;
    }

    public int getAutoMaxCharge() {
        return autoMaxCharge;
    }

    public void setAutoMaxCharge(int autoMaxCharge) {
        this.autoMaxCharge = autoMaxCharge;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public int getConnectorAmperage() {
        return connectorAmperage;
    }

    public void setConnectorAmperage(int connectorAmperage) {
        this.connectorAmperage = connectorAmperage;
    }

    public int getConnectorVoltage() {
        return connectorVoltage;
    }

    public void setConnectorVoltage(int connectorVoltage) {
        this.connectorVoltage = connectorVoltage;
    }

    public float getConnectorPower() {
        return connectorPower;
    }

    public void setConnectorPower(float connectorPower) {
        this.connectorPower = connectorPower;
    }
}
