package com.carclinic.web;

import org.springframework.stereotype.Component;

import com.carclinic.domain.ServiceType;

@Component
public class ServiceTypeMapper {

    public ServiceType toServiceType(ServiceTypeFieldsDto dto) {
        return new ServiceType(dto.getName(), dto.getDescription(), dto.getEstimatedTimeHours(), dto.getPrice());
    }

    public ServiceTypeDto toServiceTypeDto(ServiceType serviceType) {
        return new ServiceTypeDto(serviceType.getId(), serviceType.getName(), serviceType.getDescription(),
                serviceType.getEstimatedTimeHours(), serviceType.getPrice());
    }
}
