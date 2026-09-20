package com.carclinic.web;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ServiceBookingRequestDto {

    @NotNull
    private Long vehicleId;

    @NotNull
    private LocalDate appointmentDate;

    private String notes;

    @NotEmpty
    private List<Long> serviceTypeIds;

    public ServiceBookingRequestDto() {
    }

    public ServiceBookingRequestDto(Long vehicleId, LocalDate appointmentDate, String notes,
            List<Long> serviceTypeIds) {
        this.vehicleId = vehicleId;
        this.appointmentDate = appointmentDate;
        this.notes = notes;
        this.serviceTypeIds = serviceTypeIds;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getServiceTypeIds() {
        return serviceTypeIds;
    }

    public void setServiceTypeIds(List<Long> serviceTypeIds) {
        this.serviceTypeIds = serviceTypeIds;
    }
}
