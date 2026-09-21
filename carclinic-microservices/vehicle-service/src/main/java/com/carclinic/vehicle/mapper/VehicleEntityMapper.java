package com.carclinic.vehicle.mapper;

import com.carclinic.vehicle.Vehicle;
import com.carclinic.vehicle.VehicleRequest;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface VehicleEntityMapper extends Mapper<VehicleRequest, Vehicle> {

    @Override
    @Mapping(target = "id", ignore = true)
    Vehicle map(@MappingTarget Vehicle response, VehicleRequest request);
}
