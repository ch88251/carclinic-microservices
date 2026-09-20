package com.carclinic.web;

import org.springframework.stereotype.Component;

import com.carclinic.domain.Owner;
import com.carclinic.domain.OwnerRepository;
import com.carclinic.domain.Vehicle;

@Component
public class VehicleMapper {

    private final OwnerRepository ownerRepository;

    public VehicleMapper(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Vehicle toVehicle(VehicleFieldsDto dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found: " + dto.getOwnerId()));
        return new Vehicle(dto.getVin(), dto.getMake(), dto.getModel(), dto.getColor(), dto.getYear(),
                dto.getMileage(), dto.getLastServiceDate(), dto.getNextServiceDate(), owner);
    }

    public VehicleDto toVehicleDto(Vehicle vehicle) {
        Long ownerId = vehicle.getOwner() != null ? vehicle.getOwner().getId() : null;
        return new VehicleDto(vehicle.getId(), vehicle.getVin(), vehicle.getMake(), vehicle.getModel(),
                vehicle.getColor(), vehicle.getYear(), vehicle.getMileage(), vehicle.getLastServiceDate(),
                vehicle.getNextServiceDate(), ownerId);
    }
}
