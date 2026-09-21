package com.carclinic.vehicle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.carclinic.vehicle.mapper.VehicleEntityMapper;

@RequestMapping("/vehicles")
@RestController
public class VehicleResource {
    private static final Logger log = LoggerFactory.getLogger(VehicleResource.class);

    private final VehicleRepository vehicleRepository;
    private final VehicleEntityMapper vehicleEntityMapper;

    public VehicleResource(VehicleRepository vehicleRepository, VehicleEntityMapper vehicleEntityMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleEntityMapper = vehicleEntityMapper;
    }

    /**
     * Create a new vehicle.
     * @param request the vehicle creation request
     * @return the created vehicle
     */
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody VehicleRequest request) {
        log.info("Creating a new vehicle with request: {}", request);
        Vehicle vehicle = vehicleEntityMapper.map(new Vehicle(), request);
        vehicle = vehicleRepository.save(vehicle);
        log.info("Created a new vehicle: {}", vehicle);
        return ResponseEntity.ok(vehicle);
    }

    /**
     * Get a vehicle by ID.
     * @param id the ID of the vehicle
     * @return the vehicle with the given ID, or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        log.info("Fetching vehicle with id: {}", id);
        return vehicleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all vehicles.
     * @return a list of all vehicles
     */
    @GetMapping
    public ResponseEntity<Iterable<Vehicle>> getAllVehicles() {
        log.info("Fetching all vehicles");
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Delete a vehicle by ID.
     * @param id the ID of the vehicle to delete
     * @return a 204 No Content response if the vehicle was deleted, or a 404 Not Found response if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.info("Deleting vehicle with id: {}", id);
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update a vehicle by ID.
     * @param id the ID of the vehicle to update
     * @param request the vehicle update request
     * @return the updated vehicle, or a 404 Not Found response if the vehicle does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequest request) {
        log.info("Updating vehicle with id: {}", id);
        return vehicleRepository.findById(id)
                .map(existingVehicle -> {
                    Vehicle updatedVehicle = vehicleEntityMapper.map(existingVehicle, request);
                    updatedVehicle = vehicleRepository.save(updatedVehicle);
                    return ResponseEntity.ok(updatedVehicle);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
