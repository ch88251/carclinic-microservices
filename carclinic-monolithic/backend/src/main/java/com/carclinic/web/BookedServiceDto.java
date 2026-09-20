package com.carclinic.web;

import java.math.BigDecimal;

public class BookedServiceDto {

    private Long serviceTypeId;
    private String serviceTypeName;
    private BigDecimal cost;

    public BookedServiceDto() {
    }

    public BookedServiceDto(Long serviceTypeId, String serviceTypeName, BigDecimal cost) {
        this.serviceTypeId = serviceTypeId;
        this.serviceTypeName = serviceTypeName;
        this.cost = cost;
    }

    public Long getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
