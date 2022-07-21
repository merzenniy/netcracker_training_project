package com.example.chargecracker.dto;

import java.util.List;
import java.util.Map;

public class PagesStatisticDto {
    private List<String> months;
    private Map<String, List<Long>> businessOperationCount;

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public Map<String, List<Long>> getBusinessOperationCount() {
        return businessOperationCount;
    }

    public void setBusinessOperationCount(Map<String, List<Long>> businessOperationCount) {
        this.businessOperationCount = businessOperationCount;
    }
}
