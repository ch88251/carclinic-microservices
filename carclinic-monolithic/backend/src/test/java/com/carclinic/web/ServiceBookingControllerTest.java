package com.carclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.carclinic.domain.Owner;
import com.carclinic.domain.ServiceAppointmentRepository;
import com.carclinic.domain.ServiceDetails;
import com.carclinic.domain.ServiceDetailsRepository;
import com.carclinic.domain.ServiceType;
import com.carclinic.domain.ServiceTypeRepository;
import com.carclinic.domain.Vehicle;
import com.carclinic.domain.VehicleRepository;

class ServiceBookingControllerTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @Mock
    private ServiceAppointmentRepository appointmentRepository;

    @Mock
    private ServiceDetailsRepository serviceDetailsRepository;

    @Mock
    private ServiceBookingMapper serviceBookingMapper;

    @InjectMocks
    private ServiceBookingController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookServices_createsAppointmentAndOneServiceDetailPerService() {
        Owner owner = new Owner();
        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(owner);
        ServiceType oilChange = new ServiceType("Oil Change", "desc", 1, new BigDecimal("49.99"));
        ServiceType tireRotation = new ServiceType("Tire Rotation", "desc", 1, new BigDecimal("29.99"));

        ServiceBookingRequestDto request = new ServiceBookingRequestDto(
                1L, LocalDate.of(2026, 6, 1), "notes", List.of(10L, 20L));

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(serviceTypeRepository.findById(10L)).thenReturn(Optional.of(oilChange));
        when(serviceTypeRepository.findById(20L)).thenReturn(Optional.of(tireRotation));
        ServiceBookingDto expectedDto = new ServiceBookingDto();
        when(serviceBookingMapper.toServiceBookingDto(any(), any())).thenReturn(expectedDto);

        ResponseEntity<ServiceBookingDto> response = controller.bookServices(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(expectedDto, response.getBody());
        verify(appointmentRepository).save(any());
        verify(serviceDetailsRepository, times(2)).save(any(ServiceDetails.class));
    }

    @Test
    void bookServices_vehicleNotFound_throwsNotFound() {
        ServiceBookingRequestDto request = new ServiceBookingRequestDto(
                99L, LocalDate.of(2026, 6, 1), null, List.of(10L));
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.bookServices(request));

        assertEquals(404, ex.getStatusCode().value());
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void bookServices_vehicleHasNoOwner_throwsConflict() {
        Vehicle vehicle = new Vehicle();
        ServiceBookingRequestDto request = new ServiceBookingRequestDto(
                1L, LocalDate.of(2026, 6, 1), null, List.of(10L));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.bookServices(request));

        assertEquals(409, ex.getStatusCode().value());
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void bookServices_serviceTypeNotFound_throwsNotFound() {
        Owner owner = new Owner();
        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(owner);
        ServiceBookingRequestDto request = new ServiceBookingRequestDto(
                1L, LocalDate.of(2026, 6, 1), null, List.of(10L, 99L));
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(serviceTypeRepository.findById(10L))
                .thenReturn(Optional.of(new ServiceType("Oil Change", "desc", 1, new BigDecimal("49.99"))));
        when(serviceTypeRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.bookServices(request));

        assertEquals(404, ex.getStatusCode().value());
        verify(appointmentRepository, never()).save(any());
    }
}
