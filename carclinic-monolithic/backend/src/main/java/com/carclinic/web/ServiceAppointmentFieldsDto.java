package com.carclinic.web;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceAppointmentFieldsDto {

    @NotNull
    private Long vehicleId;

    @NotNull
    private Long customerId;

    private Long staffId;

    @NotNull
    private LocalDate appointmentDate;

    @NotBlank
    private String status;

    private String notes;

    public ServiceAppointmentFieldsDto() {
    }

    public ServiceAppointmentFieldsDto(Long vehicleId, Long customerId, Long staffId, LocalDate appointmentDate,
            String status, String notes) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.notes = notes;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
