package com.carclinic.web;

import org.springframework.stereotype.Component;

import com.carclinic.domain.Owner;

@Component
public class OwnerMapper {

    public Owner toOwner(OwnerFieldsDto dto) {
        return new Owner(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber());
    }

    public OwnerDto toOwnerDto(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getFirstName(), owner.getLastName(), owner.getEmail(),
                owner.getPhoneNumber());
    }
}
