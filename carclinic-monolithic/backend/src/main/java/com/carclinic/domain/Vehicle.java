package com.carclinic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name="vin", nullable = false, unique = true)
  private String vin;

  @Column(name="make", nullable = false)
  private String make;

  @Column(name="model", nullable = false)
  private String model;

  @Column(name="color", nullable = false)
  private String color;

  @Column(name="year", nullable = false)
  private int year;

  @Column(name="mileage", nullable = false)
  private int mileage;

  @Column(name="last_service_date", nullable = false)
  private String lastServiceDate;

  @Column(name="next_service_date", nullable = false)
  private String nextServiceDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner")
  private Owner owner;

  public Vehicle() {
  }

  public Vehicle(String vin, String make, String model, String color, int year, int mileage, String lastServiceDate, String nextServiceDate, Owner owner) {
    this.vin = vin;
    this.make = make;
    this.model = model;
    this.color = color;
    this.year = year;
    this.mileage = mileage;
    this.lastServiceDate = lastServiceDate;
    this.nextServiceDate = nextServiceDate;
    this.owner = owner; 
  }

  public Long getId() {
    return id;
  }

  // Getters and Setters
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

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
}
