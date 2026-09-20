package com.carclinic.web;

import java.math.BigDecimal;

public class ServiceTypeDto {

    private Long id;
    private String name;
    private String description;
    private int estimatedTimeHours;
    private BigDecimal price;

    public ServiceTypeDto() {
    }

    public ServiceTypeDto(Long id, String name, String description, int estimatedTimeHours, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.estimatedTimeHours = estimatedTimeHours;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
