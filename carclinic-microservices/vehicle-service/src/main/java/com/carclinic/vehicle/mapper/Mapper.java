package com.carclinic.vehicle.mapper;

public interface Mapper<R, E> {
    E map(E response, R request);
}
