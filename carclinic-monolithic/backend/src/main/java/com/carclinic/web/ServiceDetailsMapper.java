package com.carclinic.web;

import org.springframework.stereotype.Component;

import com.carclinic.domain.ServiceDetails;
import com.carclinic.domain.ServiceType;

@Component
public class ServiceDetailsMapper {

    public ServiceDetailsDto toServiceDetailsDto(ServiceDetails details) {
        ServiceType serviceType = details.getServiceType();
        Long appointmentId = details.getAppointment() != null ? details.getAppointment().getId() : null;
        return new ServiceDetailsDto(details.getId(), appointmentId, serviceType.getId(), serviceType.getName(),
                serviceType.getEstimatedTimeHours(), details.getCost());
    }
}
