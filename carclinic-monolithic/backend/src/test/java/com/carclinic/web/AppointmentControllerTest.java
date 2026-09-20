package com.carclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.carclinic.domain.ServiceAppointment;
import com.carclinic.domain.ServiceAppointmentRepository;
import com.carclinic.domain.ServiceDetails;
import com.carclinic.domain.ServiceDetailsRepository;
import com.carclinic.domain.ServiceType;

public class AppointmentControllerTest {

    @Mock
    private ServiceAppointmentRepository repository;

    @Mock
    private ServiceAppointmentMapper serviceAppointmentMapper;

    @Mock
    private ServiceDetailsRepository serviceDetailsRepository;

    @Mock
    private ServiceDetailsMapper serviceDetailsMapper;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAppointments_returnsAllAppointments() {
        ServiceAppointment a1 = new ServiceAppointment();
        ServiceAppointment a2 = new ServiceAppointment();
        Iterable<ServiceAppointment> appointments = List.of(a1, a2);
        ServiceAppointmentDto dto1 = new ServiceAppointmentDto();
        ServiceAppointmentDto dto2 = new ServiceAppointmentDto();
        when(repository.findAll()).thenReturn(appointments);
        when(serviceAppointmentMapper.toServiceAppointmentDto(a1)).thenReturn(dto1);
        when(serviceAppointmentMapper.toServiceAppointmentDto(a2)).thenReturn(dto2);

        List<ServiceAppointmentDto> result = appointmentController.getAppointments();

        assertNotNull(result);
        assertIterableEquals(List.of(dto1, dto2), result);
        verify(repository).findAll();
    }

    @Test
    void deleteAppointment_deletesById() {
        Long id = 5L;

        ResponseEntity<Void> response = appointmentController.deleteAppointment(id);

        assertEquals(204, response.getStatusCode().value());
        verify(repository).deleteById(id);
    }

    @Test
    void addAppointment_savesAndReturnsCreated() {
        ServiceAppointmentFieldsDto request = new ServiceAppointmentFieldsDto(
                1L, 2L, 3L, LocalDate.of(2026, 6, 1), "SCHEDULED", "Check brakes");
        ServiceAppointment appointment = new ServiceAppointment();
        ServiceAppointmentDto responseDto = new ServiceAppointmentDto();
        when(serviceAppointmentMapper.toServiceAppointment(request)).thenReturn(appointment);
        when(serviceAppointmentMapper.toServiceAppointmentDto(appointment)).thenReturn(responseDto);

        ResponseEntity<ServiceAppointmentDto> response = appointmentController.addAppointment(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseDto, response.getBody());
        verify(repository).save(appointment);
    }

    @Test
    void addAppointment_withoutStaff_savesAndReturnsCreated() {
        ServiceAppointmentFieldsDto request = new ServiceAppointmentFieldsDto(
                1L, 2L, null, LocalDate.of(2026, 6, 1), "SCHEDULED", null);
        ServiceAppointment appointment = new ServiceAppointment();
        ServiceAppointmentDto responseDto = new ServiceAppointmentDto();
        when(serviceAppointmentMapper.toServiceAppointment(request)).thenReturn(appointment);
        when(serviceAppointmentMapper.toServiceAppointmentDto(appointment)).thenReturn(responseDto);

        ResponseEntity<ServiceAppointmentDto> response = appointmentController.addAppointment(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseDto, response.getBody());
        verify(repository).save(appointment);
    }

    @Test
    void getAppointmentServices_returnsServicesForAppointment() {
        Long appointmentId = 1L;
        ServiceType oilChange = new ServiceType("Oil Change", "desc", 1, new BigDecimal("49.99"));
        ServiceDetails details = new ServiceDetails(null, oilChange, new BigDecimal("49.99"));
        ServiceDetailsDto dto = new ServiceDetailsDto(1L, appointmentId, null, "Oil Change", 1,
                new BigDecimal("49.99"));

        when(repository.existsById(appointmentId)).thenReturn(true);
        when(serviceDetailsRepository.findByAppointmentId(appointmentId)).thenReturn(List.of(details));
        when(serviceDetailsMapper.toServiceDetailsDto(details)).thenReturn(dto);

        List<ServiceDetailsDto> result = appointmentController.getAppointmentServices(appointmentId);

        assertNotNull(result);
        assertIterableEquals(List.of(dto), result);
    }

    @Test
    void getAppointmentServices_appointmentNotFound_throwsNotFound() {
        Long appointmentId = 99L;
        when(repository.existsById(appointmentId)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> appointmentController.getAppointmentServices(appointmentId));

        assertEquals(404, ex.getStatusCode().value());
    }
}
