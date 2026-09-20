package com.carclinic.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "owners")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Owner extends Person {

    public Owner() {
    }

    public Owner(String firstName, String lastName, String email, String phone) {
        super(firstName, lastName, email, phone);
    }

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private List<Vehicle> vehicles;

}
