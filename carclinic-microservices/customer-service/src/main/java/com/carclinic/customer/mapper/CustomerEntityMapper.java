package com.carclinic.customer.mapper;

import com.carclinic.customer.Customer;
import com.carclinic.customer.CustomerRequest;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between CustomerRequest and Customer entities.
 * It uses MapStruct to generate the implementation at compile time.
 * CustomerEntityMapper
 */
@org.mapstruct.Mapper(componentModel = "spring")
public interface CustomerEntityMapper extends Mapper<CustomerRequest, Customer> {

	@Override
	@Mapping(target = "id", ignore = true)
	Customer map(@MappingTarget Customer response, CustomerRequest request);
}
