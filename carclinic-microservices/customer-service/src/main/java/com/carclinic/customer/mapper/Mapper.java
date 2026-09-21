package com.carclinic.customer.mapper;

public interface Mapper<R, E> {
    E map(E response, R request);
}
