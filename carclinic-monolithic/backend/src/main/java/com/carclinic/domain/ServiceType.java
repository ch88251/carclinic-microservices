package com.carclinic.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_types")
public class ServiceType extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "estimated_time_hours", nullable = false)
    private int estimatedTimeHours;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public ServiceType() {
    }

    public ServiceType(String name, String description, int estimatedTimeHours, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.estimatedTimeHours = estimatedTimeHours;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedTimeHours() {
        return estimatedTimeHours;
    }

    public void setEstimatedTimeHours(int estimatedTimeHours) {
        this.estimatedTimeHours = estimatedTimeHours;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
