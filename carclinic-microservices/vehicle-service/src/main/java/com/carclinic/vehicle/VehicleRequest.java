package com.carclinic.vehicle;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(
    @NotBlank String vin,
    @NotBlank String make,
    @NotBlank String model,
    @NotBlank String color,
    int year,
    int mileage,
    LocalDate lastServiceDate,
    LocalDate nextServiceDate,
    @NotNull Long customerId
) {
}
