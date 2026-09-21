package com.carclinic.vehicle;

import java.time.LocalDate;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.carclinic.vehicle.mapper.VehicleEntityMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(VehicleResource.class)
public class VehicleResourceTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    VehicleRepository vehicleRepository;

    @MockitoBean
    VehicleEntityMapper vehicleEntityMapper;

    @Test
    void shouldGetVehicle() throws Exception {
        Vehicle vehicle = createVehicle();
        given(vehicleRepository.findById(vehicle.getId())).willReturn(java.util.Optional.of(vehicle));

        mockMvc.perform(get("/vehicles/" + vehicle.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(vehicle.getId()))
               .andExpect(jsonPath("$.vin").value(vehicle.getVin()))
               .andExpect(jsonPath("$.make").value(vehicle.getMake()))
               .andExpect(jsonPath("$.model").value(vehicle.getModel()))
               .andExpect(jsonPath("$.customerId").value(vehicle.getCustomerId()));
    }

    @Test
    void shouldNotGetVehicleWithInvalidId() throws Exception {
        given(vehicleRepository.findById(999L)).willReturn(java.util.Optional.empty());

        mockMvc.perform(get("/vehicles/999"))
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllVehicles() throws Exception {
        Vehicle vehicle = createVehicle();
        given(vehicleRepository.findAll()).willReturn(java.util.List.of(vehicle));

        mockMvc.perform(get("/vehicles"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(vehicle.getId()))
               .andExpect(jsonPath("$[0].vin").value(vehicle.getVin()));
    }

    @Test
    void shouldGetNoVehicles() throws Exception {
        given(vehicleRepository.findAll()).willReturn(java.util.List.of());

        mockMvc.perform(get("/vehicles"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldCreateVehicle() throws Exception {
        Vehicle vehicle = createVehicle();
        given(vehicleEntityMapper.map(any(Vehicle.class), any(VehicleRequest.class))).willReturn(vehicle);
        given(vehicleRepository.save(any(Vehicle.class))).willReturn(vehicle);

        mockMvc.perform(post("/vehicles")
               .contentType("application/json")
               .content(vehicleJson(vehicle)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(vehicle.getId()))
               .andExpect(jsonPath("$.vin").value(vehicle.getVin()));
    }

    @Test
    void shouldUpdateVehicle() throws Exception {
        Vehicle vehicle = createVehicle();
        given(vehicleRepository.findById(vehicle.getId())).willReturn(java.util.Optional.of(vehicle));
        given(vehicleEntityMapper.map(any(Vehicle.class), any(VehicleRequest.class))).willReturn(vehicle);
        given(vehicleRepository.save(any(Vehicle.class))).willReturn(vehicle);

        mockMvc.perform(put("/vehicles/" + vehicle.getId())
               .contentType("application/json")
               .content(vehicleJson(vehicle)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(vehicle.getId()));
    }

    @Test
    void shouldDeleteVehicle() throws Exception {
        Vehicle vehicle = createVehicle();
        given(vehicleRepository.existsById(vehicle.getId())).willReturn(true);

        mockMvc.perform(delete("/vehicles/" + vehicle.getId()))
               .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingVehicle() throws Exception {
        given(vehicleRepository.existsById(1L)).willReturn(false);

        mockMvc.perform(delete("/vehicles/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingVehicle() throws Exception {
        given(vehicleRepository.findById(1L)).willReturn(java.util.Optional.empty());

        mockMvc.perform(put("/vehicles/1")
               .contentType("application/json")
               .content(vehicleJson(createVehicle())))
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenCreatingVehicleWithInvalidData() throws Exception {
        mockMvc.perform(post("/vehicles")
               .contentType("application/json")
               .content("{\"vin\":\"\",\"make\":\"\",\"model\":\"\",\"color\":\"\"}"))
               .andExpect(status().isBadRequest());
    }

    private String vehicleJson(Vehicle vehicle) {
        return "{\"vin\":\"" + vehicle.getVin() + "\",\"make\":\"" + vehicle.getMake() + "\",\"model\":\""
                + vehicle.getModel() + "\",\"color\":\"" + vehicle.getColor() + "\",\"year\":" + vehicle.getYear()
                + ",\"mileage\":" + vehicle.getMileage() + ",\"customerId\":" + vehicle.getCustomerId() + "}";
    }

    private Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setVin("1HGCM82633A004352");
        vehicle.setMake("Honda");
        vehicle.setModel("Accord");
        vehicle.setColor("Blue");
        vehicle.setYear(2018);
        vehicle.setMileage(42000);
        vehicle.setLastServiceDate(LocalDate.now().minusMonths(3));
        vehicle.setNextServiceDate(LocalDate.now().plusMonths(3));
        vehicle.setCustomerId(1L);
        return vehicle;
    }
}
