package com.carclinic.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleFieldsDto {

    @NotBlank
    private String vin;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @Min(1886)
    @Max(2200)
    private int year;

    @Min(0)
    private int mileage;

    @NotBlank
    private String lastServiceDate;

    @NotBlank
    private String nextServiceDate;

    @NotNull
    private Long ownerId;

    public VehicleFieldsDto() {
    }

    public VehicleFieldsDto(String vin, String make, String model, String color, int year, int mileage,
            String lastServiceDate, String nextServiceDate, Long ownerId) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
        this.mileage = mileage;
        this.lastServiceDate = lastServiceDate;
        this.nextServiceDate = nextServiceDate;
        this.ownerId = ownerId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public String getNextServiceDate() {
        return nextServiceDate;
    }

    public void setNextServiceDate(String nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
