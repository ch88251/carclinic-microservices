package com.carclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.carclinic.domain.ServiceType;
import com.carclinic.domain.ServiceTypeRepository;

public class ServiceTypeControllerTest {
    
    @InjectMocks
    private ServiceTypeController serviceTypeController;

    @Mock
    private ServiceTypeRepository serviceTypeRepository;

    @Mock
    private ServiceTypeMapper serviceTypeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getServiceTypes_returnsAllServiceTypes() {
        ServiceType st1 = new ServiceType();
        ServiceType st2 = new ServiceType();
        List<ServiceType> serviceTypes = List.of(st1, st2);
        ServiceTypeDto dto1 = new ServiceTypeDto();
        ServiceTypeDto dto2 = new ServiceTypeDto();
        when(serviceTypeRepository.findAll()).thenReturn(serviceTypes);
        when(serviceTypeMapper.toServiceTypeDto(st1)).thenReturn(dto1);
        when(serviceTypeMapper.toServiceTypeDto(st2)).thenReturn(dto2);
        List<ServiceTypeDto> result = serviceTypeController.getServiceTypes();
        assertNotNull(result);
        assertIterableEquals(List.of(dto1, dto2), result);
        verify(serviceTypeRepository).findAll();
    }

    @Test
    void deleteServiceType_deletesById() {
        Long id = 1L;
        serviceTypeController.deleteServiceType(id);
        verify(serviceTypeRepository).deleteById(id);
    }

    @Test
    void addServiceType_savesAndReturnsServiceType() {
        ServiceTypeFieldsDto request = new ServiceTypeFieldsDto("Oil Change", "Standard oil change", 1,
                new BigDecimal("49.99"));
        ServiceType serviceType = new ServiceType();
        ServiceTypeDto serviceTypeDto = new ServiceTypeDto();
        when(serviceTypeMapper.toServiceType(request)).thenReturn(serviceType);
        when(serviceTypeMapper.toServiceTypeDto(serviceType)).thenReturn(serviceTypeDto);
        ResponseEntity<ServiceTypeDto> response = serviceTypeController.addServiceType(request);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(serviceTypeDto, response.getBody());
        verify(serviceTypeRepository).save(serviceType);
    }

}
