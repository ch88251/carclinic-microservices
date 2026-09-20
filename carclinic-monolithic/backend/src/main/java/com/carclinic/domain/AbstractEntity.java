package com.carclinic.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
  
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "date_created", nullable = false, updatable = false)
    private Instant dateCreated = Instant.now();

    @Column(name = "date_modified", nullable = false)
    private Instant dateModified = Instant.now();

    public Long getId() {
        return id;
    }
}
