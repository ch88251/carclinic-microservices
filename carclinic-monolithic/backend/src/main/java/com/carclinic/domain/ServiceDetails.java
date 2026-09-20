package com.carclinic.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_details")
public class ServiceDetails extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private ServiceAppointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @Column(name = "cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    public ServiceDetails() {
    }

    public ServiceDetails(ServiceAppointment appointment, ServiceType serviceType, BigDecimal cost) {
        this.appointment = appointment;
        this.serviceType = serviceType;
        this.cost = cost;
    }

    public ServiceAppointment getAppointment() {
        return appointment;
    }

    public void setAppointment(ServiceAppointment appointment) {
        this.appointment = appointment;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
