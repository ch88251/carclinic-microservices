package com.carclinic.web;

import com.carclinic.domain.Vehicle;
import com.carclinic.domain.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleControllerTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getVehicles_returnsAllVehicles() {
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Vehicle();
        List<Vehicle> vehicles = Arrays.asList(v1, v2);
        VehicleDto dto1 = new VehicleDto();
        VehicleDto dto2 = new VehicleDto();
        when(vehicleRepository.findAll()).thenReturn(vehicles);
        when(vehicleMapper.toVehicleDto(v1)).thenReturn(dto1);
        when(vehicleMapper.toVehicleDto(v2)).thenReturn(dto2);

        List<VehicleDto> result = vehicleController.getVehicles();
        assertNotNull(result);
        assertIterableEquals(List.of(dto1, dto2), result);
        verify(vehicleRepository).findAll();
    }

    @Test
    void getVehicleById_returnsVehicle() {
        Long id = 1L;
        Vehicle vehicle = new Vehicle();
        VehicleDto vehicleDto = new VehicleDto();
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toVehicleDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<VehicleDto> response = vehicleController.getVehicleById(id);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(vehicleDto, response.getBody());
    }

    @Test
    void getVehicleById_returnsNotFound_whenVehicleDoesNotExist() {
        Long id = 99L;
        when(vehicleRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> vehicleController.getVehicleById(id)
        );
        assertEquals(404, ex.getStatusCode().value());
    }

    @Test
    void deleteVehicle_deletesById() {
        Long id = 1L;
        ResponseEntity<Void> response = vehicleController.deleteVehicle(id);
        assertEquals(204, response.getStatusCode().value());
        verify(vehicleRepository).deleteById(id);
    }

    @Test
    void addVehicle_savesAndReturnsVehicle() {
        VehicleFieldsDto request = new VehicleFieldsDto("VIN1", "Toyota", "Camry", "Blue", 2020, 5000,
                "2024-01-01", "2025-01-01", 1L);
        Vehicle vehicle = new Vehicle();
        VehicleDto vehicleDto = new VehicleDto();
        when(vehicleMapper.toVehicle(request)).thenReturn(vehicle);
        when(vehicleMapper.toVehicleDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<VehicleDto> response = vehicleController.addVehicle(request);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(vehicleDto, response.getBody());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void updateVehicle_updatesAndReturnsVehicle() {
        Long id = 1L;
        VehicleFieldsDto request = new VehicleFieldsDto("VIN1", "Toyota", "Camry", "Blue", 2020, 5000,
                "2024-01-01", "2025-01-01", 1L);
        Vehicle vehicle = new Vehicle();
        VehicleDto vehicleDto = new VehicleDto();
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toVehicle(request)).thenReturn(vehicle);
        when(vehicleMapper.toVehicleDto(vehicle)).thenReturn(vehicleDto);

        ResponseEntity<VehicleDto> response = vehicleController.updateVehicle(id, request);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(vehicleDto, response.getBody());
        verify(vehicleRepository).save(vehicle);
    }
}
