package com.carclinic.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.carclinic.domain.Owner;
import com.carclinic.domain.ServiceAppointment;
import com.carclinic.domain.ServiceAppointmentRepository;
import com.carclinic.domain.ServiceDetails;
import com.carclinic.domain.ServiceDetailsRepository;
import com.carclinic.domain.ServiceType;
import com.carclinic.domain.ServiceTypeRepository;
import com.carclinic.domain.Vehicle;
import com.carclinic.domain.VehicleRepository;

import jakarta.validation.Valid;

/**
 * Records which services staff performed on a customer's vehicle: creates a service
 * appointment and one service-detail charge line per selected service type, at that
 * service type's catalog price. This only stores the booking - invoicing/billing is not
 * implemented here.
 */
@RestController
@RequestMapping("/api/service-bookings")
@Validated
public class ServiceBookingController {

    private final VehicleRepository vehicleRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceAppointmentRepository appointmentRepository;
    private final ServiceDetailsRepository serviceDetailsRepository;
    private final ServiceBookingMapper serviceBookingMapper;

    public ServiceBookingController(VehicleRepository vehicleRepository, ServiceTypeRepository serviceTypeRepository,
            ServiceAppointmentRepository appointmentRepository, ServiceDetailsRepository serviceDetailsRepository,
            ServiceBookingMapper serviceBookingMapper) {
        this.vehicleRepository = vehicleRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.appointmentRepository = appointmentRepository;
        this.serviceDetailsRepository = serviceDetailsRepository;
        this.serviceBookingMapper = serviceBookingMapper;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<ServiceBookingDto> bookServices(@Valid @RequestBody ServiceBookingRequestDto request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Vehicle not found: " + request.getVehicleId()));

        Owner customer = vehicle.getOwner();
        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Vehicle has no owner on file: " + vehicle.getId());
        }

        List<ServiceType> serviceTypes = new ArrayList<>();
        for (Long serviceTypeId : request.getServiceTypeIds()) {
            ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Service type not found: " + serviceTypeId));
            serviceTypes.add(serviceType);
        }

        ServiceAppointment appointment = new ServiceAppointment(vehicle, customer, null, request.getAppointmentDate(),
                "SCHEDULED", request.getNotes());
        appointmentRepository.save(appointment);

        List<ServiceDetails> details = new ArrayList<>();
        for (ServiceType serviceType : serviceTypes) {
            ServiceDetails serviceDetails = new ServiceDetails(appointment, serviceType, serviceType.getPrice());
            serviceDetailsRepository.save(serviceDetails);
            details.add(serviceDetails);
        }

        ServiceBookingDto response = serviceBookingMapper.toServiceBookingDto(appointment, details);
        URI location = UriComponentsBuilder.fromPath("/api/appointments/{id}")
                .buildAndExpand(appointment.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
