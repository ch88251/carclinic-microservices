package com.carclinic.customer.web.mapper;

public interface Mapper<R, E> {
    E map(E response, R request);
}
