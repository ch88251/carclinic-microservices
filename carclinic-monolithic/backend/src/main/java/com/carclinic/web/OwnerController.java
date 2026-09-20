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

import com.carclinic.domain.Owner;
import com.carclinic.domain.OwnerRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/owners")
@Validated
public class OwnerController {

    private final OwnerRepository repository;
    private final OwnerMapper ownerMapper;

    public OwnerController(OwnerRepository repository, OwnerMapper ownerMapper) {
        this.repository = repository;
        this.ownerMapper = ownerMapper;
    }

    @GetMapping(produces = "application/json")
    public List<OwnerDto> getOwners() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(ownerMapper::toOwnerDto)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<OwnerDto> addOwner(@Valid @RequestBody OwnerFieldsDto request) {
        Owner owner = ownerMapper.toOwner(request);
        repository.save(owner);

        OwnerDto response = ownerMapper.toOwnerDto(owner);
        URI location = UriComponentsBuilder.fromPath("/api/owners/{id}")
                .buildAndExpand(owner.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
