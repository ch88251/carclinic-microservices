package com.carclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.carclinic.domain.Owner;
import com.carclinic.domain.OwnerRepository;
import com.carclinic.domain.ServiceAppointment;
import com.carclinic.domain.Staff;
import com.carclinic.domain.StaffRepository;
import com.carclinic.domain.Vehicle;
import com.carclinic.domain.VehicleRepository;

public class ServiceAppointmentMapperTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private ServiceAppointmentMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toServiceAppointment_mapsAllFields() {
        Vehicle vehicle = new Vehicle();
        Owner owner = new Owner();
        Staff staff = new Staff();
        ServiceAppointmentFieldsDto dto = new ServiceAppointmentFieldsDto(
                1L, 2L, 3L, LocalDate.of(2026, 6, 1), "SCHEDULED", "Oil change");
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(ownerRepository.findById(2L)).thenReturn(Optional.of(owner));
        when(staffRepository.findById(3L)).thenReturn(Optional.of(staff));

        ServiceAppointment result = mapper.toServiceAppointment(dto);

        assertEquals(vehicle, result.getVehicle());
        assertEquals(owner, result.getCustomer());
        assertEquals(staff, result.getStaff());
        assertEquals(LocalDate.of(2026, 6, 1), result.getAppointmentDate());
        assertEquals("SCHEDULED", result.getStatus());
        assertEquals("Oil change", result.getNotes());
    }

    @Test
    void toServiceAppointment_withNullStaffId_setsStaffNull() {
        Vehicle vehicle = new Vehicle();
        Owner owner = new Owner();
        ServiceAppointmentFieldsDto dto = new ServiceAppointmentFieldsDto(
                1L, 2L, null, LocalDate.of(2026, 6, 1), "SCHEDULED", null);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(ownerRepository.findById(2L)).thenReturn(Optional.of(owner));

        ServiceAppointment result = mapper.toServiceAppointment(dto);

        assertNull(result.getStaff());
    }

    @Test
    void toServiceAppointment_vehicleNotFound_throwsException() {
        ServiceAppointmentFieldsDto dto = new ServiceAppointmentFieldsDto(
                99L, 2L, null, LocalDate.of(2026, 6, 1), "SCHEDULED", null);
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> mapper.toServiceAppointment(dto));
    }

    @Test
    void toServiceAppointment_ownerNotFound_throwsException() {
        Vehicle vehicle = new Vehicle();
        ServiceAppointmentFieldsDto dto = new ServiceAppointmentFieldsDto(
                1L, 99L, null, LocalDate.of(2026, 6, 1), "SCHEDULED", null);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(ownerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> mapper.toServiceAppointment(dto));
    }

    @Test
    void toServiceAppointment_staffNotFound_throwsException() {
        Vehicle vehicle = new Vehicle();
        Owner owner = new Owner();
        ServiceAppointmentFieldsDto dto = new ServiceAppointmentFieldsDto(
                1L, 2L, 99L, LocalDate.of(2026, 6, 1), "SCHEDULED", null);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(ownerRepository.findById(2L)).thenReturn(Optional.of(owner));
        when(staffRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> mapper.toServiceAppointment(dto));
    }

    @Test
    void toServiceAppointmentDto_mapsAllFields() {
        Vehicle vehicle = new Vehicle();
        Owner owner = new Owner();
        Staff staff = new Staff();
        ServiceAppointment appointment = new ServiceAppointment(
                vehicle, owner, staff, LocalDate.of(2026, 6, 1), "SCHEDULED", "Oil change");

        ServiceAppointmentDto result = mapper.toServiceAppointmentDto(appointment);

        assertEquals(LocalDate.of(2026, 6, 1), result.getAppointmentDate());
        assertEquals("SCHEDULED", result.getStatus());
        assertEquals("Oil change", result.getNotes());
    }

    @Test
    void toServiceAppointmentDto_withNullAssociations_returnsNullIds() {
        ServiceAppointment appointment = new ServiceAppointment(
                null, null, null, LocalDate.of(2026, 6, 1), "PENDING", null);

        ServiceAppointmentDto result = mapper.toServiceAppointmentDto(appointment);

        assertNull(result.getVehicleId());
        assertNull(result.getCustomerId());
        assertNull(result.getStaffId());
    }
}
