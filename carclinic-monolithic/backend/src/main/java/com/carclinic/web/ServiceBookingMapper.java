package com.carclinic.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.carclinic.domain.Owner;
import com.carclinic.domain.ServiceAppointment;
import com.carclinic.domain.ServiceDetails;
import com.carclinic.domain.Vehicle;

@Component
public class ServiceBookingMapper {

    public ServiceBookingDto toServiceBookingDto(ServiceAppointment appointment, List<ServiceDetails> details) {
        ServiceBookingDto dto = new ServiceBookingDto();
        dto.setAppointmentId(appointment.getId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStatus(appointment.getStatus());
        dto.setNotes(appointment.getNotes());

        Vehicle vehicle = appointment.getVehicle();
        if (vehicle != null) {
            dto.setVehicleId(vehicle.getId());
            dto.setVehicleDescription(vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
        }

        Owner customer = appointment.getCustomer();
        if (customer != null) {
            dto.setCustomerId(customer.getId());
            dto.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
        }

        List<BookedServiceDto> services = details.stream()
                .map(d -> new BookedServiceDto(d.getServiceType().getId(), d.getServiceType().getName(), d.getCost()))
                .toList();
        dto.setServices(services);
        dto.setTotalCost(services.stream()
                .map(BookedServiceDto::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return dto;
    }
}
