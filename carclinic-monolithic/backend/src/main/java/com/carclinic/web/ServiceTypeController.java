package com.carclinic.web;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.carclinic.domain.ServiceType;
import com.carclinic.domain.ServiceTypeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/service-types")
@Validated
public class ServiceTypeController {

    private final ServiceTypeRepository repository;
    private final ServiceTypeMapper serviceTypeMapper;

    public ServiceTypeController(ServiceTypeRepository repository, ServiceTypeMapper serviceTypeMapper) {
        this.repository = repository;
        this.serviceTypeMapper = serviceTypeMapper;
    }

    @GetMapping(produces = "application/json")
    public List<ServiceTypeDto> getServiceTypes() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(serviceTypeMapper::toServiceTypeDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ServiceTypeDto> addServiceType(@Valid @RequestBody ServiceTypeFieldsDto request) {
        ServiceType serviceType = serviceTypeMapper.toServiceType(request);
        repository.save(serviceType);

        ServiceTypeDto response = serviceTypeMapper.toServiceTypeDto(serviceType);
        URI location = UriComponentsBuilder.fromPath("/api/service-types/{id}")
                .buildAndExpand(serviceType.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
