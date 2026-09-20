package com.carclinic.web;

import org.springframework.stereotype.Component;

import com.carclinic.domain.Staff;

@Component
public class StaffMapper {

    public Staff toStaff(StaffFieldsDto dto) {
        return new Staff(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getRole());
    }

    public StaffDto toStaffDto(Staff staff) {
        return new StaffDto(staff.getId(), staff.getFirstName(), staff.getLastName(), staff.getEmail(),
                staff.getPhoneNumber(), staff.getRole());
    }
}
