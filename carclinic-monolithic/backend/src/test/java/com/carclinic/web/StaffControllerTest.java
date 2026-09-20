package com.carclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.carclinic.domain.Staff;
import com.carclinic.domain.StaffRepository;

public class StaffControllerTest {
    
    @Mock
    private StaffRepository staffRepository;

    @Mock
    private StaffMapper staffMapper;

    @InjectMocks
    private StaffController staffController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStaff_returnsAllStaff() {
        Staff s1 = new Staff();
        Staff s2 = new Staff();
        Iterable<Staff> staffList = List.of(s1, s2);
        StaffDto dto1 = new StaffDto();
        StaffDto dto2 = new StaffDto();
        when(staffRepository.findAll()).thenReturn(staffList);
        when(staffMapper.toStaffDto(s1)).thenReturn(dto1);
        when(staffMapper.toStaffDto(s2)).thenReturn(dto2);
        List<StaffDto> result = staffController.getStaff();
        assertNotNull(result);
        assertIterableEquals(List.of(dto1, dto2), result);
        verify(staffRepository).findAll();
    }

    @Test
    void deleteStaff_deletesById() {
        Long id = 1L;
        ResponseEntity<Void> response = staffController.deleteStaff(id);
        assertEquals(204, response.getStatusCode().value());
        verify(staffRepository).deleteById(id);
    }

    @Test
    void addStaff_savesAndReturnsStaff() {
        StaffFieldsDto request = new StaffFieldsDto("John", "Doe", "john@example.com", "555-1234", "Mechanic");
        Staff staff = new Staff();
        StaffDto staffDto = new StaffDto();
        when(staffMapper.toStaff(request)).thenReturn(staff);
        when(staffMapper.toStaffDto(staff)).thenReturn(staffDto);
        ResponseEntity<StaffDto> response = staffController.addStaff(request);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(staffDto, response.getBody());
        verify(staffRepository).save(staff);
    }

}
