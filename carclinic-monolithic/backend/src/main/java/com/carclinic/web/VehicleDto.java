package com.carclinic.web;

public class VehicleDto {

    private Long id;
    private String vin;
    private String make;
    private String model;
    private String color;
    private int year;
    private int mileage;
    private String lastServiceDate;
    private String nextServiceDate;
    private Long ownerId;

    public VehicleDto() {
    }

    public VehicleDto(Long id, String vin, String make, String model, String color, int year, int mileage,
            String lastServiceDate, String nextServiceDate, Long ownerId) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
