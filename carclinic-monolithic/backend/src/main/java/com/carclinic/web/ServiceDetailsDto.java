package com.carclinic.web;

import java.math.BigDecimal;

public class ServiceDetailsDto {

    private Long id;
    private Long appointmentId;
    private Long serviceTypeId;
    private String serviceTypeName;
    private int estimatedTimeHours;
    private BigDecimal cost;

    public ServiceDetailsDto() {
    }

    public ServiceDetailsDto(Long id, Long appointmentId, Long serviceTypeId, String serviceTypeName,
            int estimatedTimeHours, BigDecimal cost) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.serviceTypeId = serviceTypeId;
        this.serviceTypeName = serviceTypeName;
        this.estimatedTimeHours = estimatedTimeHours;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
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

    public int getEstimatedTimeHours() {
        return estimatedTimeHours;
    }

    public void setEstimatedTimeHours(int estimatedTimeHours) {
        this.estimatedTimeHours = estimatedTimeHours;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
