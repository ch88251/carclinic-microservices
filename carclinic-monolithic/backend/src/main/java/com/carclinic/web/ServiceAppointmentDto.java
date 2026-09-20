package com.carclinic.web;

import java.time.LocalDate;

public class ServiceAppointmentDto {

    private Long id;
    private Long vehicleId;
    private Long customerId;
    private Long staffId;
    private LocalDate appointmentDate;
    private String status;
    private String notes;

    public ServiceAppointmentDto() {
    }

    public ServiceAppointmentDto(Long id, Long vehicleId, Long customerId, Long staffId, LocalDate appointmentDate,
            String status, String notes) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Display-only fields for list views

    private String customerName;
    private String vehicleDescription;
    private String staffName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
