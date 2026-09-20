package com.carclinic.web;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceTypeFieldsDto {

    @NotBlank
    private String name;

    private String description;

    @Min(1)
    private int estimatedTimeHours;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    public ServiceTypeFieldsDto() {
    }

    public ServiceTypeFieldsDto(String name, String description, int estimatedTimeHours, BigDecimal price) {
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
