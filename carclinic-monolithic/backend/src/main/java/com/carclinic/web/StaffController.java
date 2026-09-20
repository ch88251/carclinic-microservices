package com.carclinic.web;

import java.net.URI;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.carclinic.domain.Staff;
import com.carclinic.domain.StaffRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/staff")
@Validated
public class StaffController {

  private final StaffRepository repository;
  private final StaffMapper staffMapper;

  public StaffController(StaffRepository repository, StaffMapper staffMapper) {
    this.repository = repository;
    this.staffMapper = staffMapper;
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<StaffDto> addStaff(@Valid @RequestBody StaffFieldsDto request) {
    Staff staff = staffMapper.toStaff(request);
    repository.save(staff);

    StaffDto response = staffMapper.toStaffDto(staff);
    URI location = UriComponentsBuilder.fromPath("/api/staff/{id}")
        .buildAndExpand(staff.getId())
        .toUri();

    return ResponseEntity.created(location).body(response);
  }

  @GetMapping(produces = "application/json")
  public List<StaffDto> getStaff() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(staffMapper::toStaffDto)
        .toList();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
