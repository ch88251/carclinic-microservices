package com.carclinic.customer.web.mapper;

import com.carclinic.customer.model.Customer;
import com.carclinic.customer.web.CustomerRequest;
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
	@Mapping(target = "firstName", ignore = true)
	@Mapping(target = "lastName", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "phone", ignore = true)
	Customer map(@MappingTarget Customer response, CustomerRequest request);
}
