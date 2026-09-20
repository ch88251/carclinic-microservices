package com.carclinic.web;

import org.springframework.stereotype.Component;

import com.carclinic.domain.Owner;
import com.carclinic.domain.OwnerRepository;
import com.carclinic.domain.ServiceAppointment;
import com.carclinic.domain.Staff;
import com.carclinic.domain.StaffRepository;
import com.carclinic.domain.Vehicle;
import com.carclinic.domain.VehicleRepository;

@Component
public class ServiceAppointmentMapper {

    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;
    private final StaffRepository staffRepository;

    public ServiceAppointmentMapper(VehicleRepository vehicleRepository, OwnerRepository ownerRepository,
            StaffRepository staffRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
        this.staffRepository = staffRepository;
    }

    public ServiceAppointment toServiceAppointment(ServiceAppointmentFieldsDto dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + dto.getVehicleId()));
        Owner customer = ownerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + dto.getCustomerId()));
        Staff staff = null;
        if (dto.getStaffId() != null) {
            staff = staffRepository.findById(dto.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("Staff not found: " + dto.getStaffId()));
        }
        return new ServiceAppointment(vehicle, customer, staff, dto.getAppointmentDate(), dto.getStatus(),
                dto.getNotes());
    }

    public ServiceAppointmentDto toServiceAppointmentDto(ServiceAppointment appointment) {
        Long vehicleId = appointment.getVehicle() != null ? appointment.getVehicle().getId() : null;
        Long customerId = appointment.getCustomer() != null ? appointment.getCustomer().getId() : null;
        Long staffId = appointment.getStaff() != null ? appointment.getStaff().getId() : null;
        ServiceAppointmentDto dto = new ServiceAppointmentDto(appointment.getId(), vehicleId, customerId, staffId,
                appointment.getAppointmentDate(), appointment.getStatus(), appointment.getNotes());

        if (appointment.getCustomer() != null) {
            dto.setCustomerName(
                    appointment.getCustomer().getFirstName() + " " + appointment.getCustomer().getLastName());
        }
        if (appointment.getVehicle() != null) {
            var v = appointment.getVehicle();
            dto.setVehicleDescription(v.getYear() + " " + v.getMake() + " " + v.getModel());
        }
        if (appointment.getStaff() != null) {
            dto.setStaffName(
                    appointment.getStaff().getFirstName() + " " + appointment.getStaff().getLastName());
        }

        return dto;
    }
}
