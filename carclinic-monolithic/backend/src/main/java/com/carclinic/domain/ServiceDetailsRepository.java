package com.carclinic.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ServiceDetailsRepository extends CrudRepository<ServiceDetails, Long> {

    List<ServiceDetails> findByAppointmentId(Long appointmentId);
}
