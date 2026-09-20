package com.carclinic;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carclinic.domain.VehicleRepository;
import com.carclinic.domain.OwnerRepository;
import com.carclinic.domain.ServiceType;
import com.carclinic.domain.ServiceTypeRepository;
import com.carclinic.domain.Vehicle;
import com.carclinic.domain.Owner;

@SpringBootApplication
public class CarclinicApplication implements CommandLineRunner {

	private final VehicleRepository vehicleRepository;
	private final OwnerRepository ownerRepository;
	private final ServiceTypeRepository serviceTypeRepository;

	public CarclinicApplication(VehicleRepository vehicleRepository, OwnerRepository ownerRepository,
			ServiceTypeRepository serviceTypeRepository) {
		this.vehicleRepository = vehicleRepository;
		this.ownerRepository = ownerRepository;
		this.serviceTypeRepository = serviceTypeRepository;
	}

  public static void main(String[] args) {
    SpringApplication.run(CarclinicApplication.class, args);

  }

  public void run(String... args) throws Exception {
    // Add owner objects and save these to db
    ownerRepository.save(new Owner("John", "Doe", "john.doe@example.com", "303-555-1234"));
    ownerRepository.save(new Owner("Jane", "Smith", "jane.smith@example.com", "303-555-2345"));
    ownerRepository.save(new Owner("Emily", "Johnson", "emily.johnson@example.com", "303-555-3456"));
    ownerRepository.save(new Owner("Michael", "Williams", "michael.williams@example.com", "303-555-4567"));
    ownerRepository.save(new Owner("Sarah", "Brown", "sarah.brown@example.com", "303-555-5678"));

    // Add vehicle objects and save these to db
    vehicleRepository.save(new Vehicle("1HGBH41JXMN289878", "Hyundai", "Elantra", "Blue", 2020, 15000, LocalDate.of(2023, 6, 15).toString(), LocalDate.of(2024, 6, 15).toString(), ownerRepository.findById(1L).orElse(null)));
    vehicleRepository.save(new Vehicle("2HGBH41JXMN289879", "Toyota", "Camry", "Red", 2019, 20000, LocalDate.of(2023, 7, 20).toString(), LocalDate.of(2024, 7, 20).toString(), ownerRepository.findById(2L).orElse(null)));
    vehicleRepository.save(new Vehicle("3HGBH41JXMN289880", "Honda", "Civic", "Black", 2021, 10000, LocalDate.of(2023, 8, 10).toString(), LocalDate.of(2024, 8, 10).toString(), ownerRepository.findById(3L).orElse(null)));
    vehicleRepository.save(new Vehicle("4HGBH41JXMN289881", "Ford", "Focus", "White", 2018, 25000, LocalDate.of(2023, 9, 5).toString(), LocalDate.of(2024, 9, 5).toString(), ownerRepository.findById(4L).orElse(null)));
    vehicleRepository.save(new Vehicle("5HGBH41JXMN289882", "Chevrolet", "Malibu", "Silver", 2022, 5000, LocalDate.of(2023, 10, 1).toString(), LocalDate.of(2024, 10, 1).toString(), ownerRepository.findById(5L).orElse(null)));

    // Add service type objects and save these to db
    serviceTypeRepository.save(new ServiceType("Oil Change", "Standard engine oil and filter replacement", 1, new BigDecimal("49.99")));
    serviceTypeRepository.save(new ServiceType("Tire Rotation", "Rotate all four tires for even wear", 1, new BigDecimal("29.99")));
    serviceTypeRepository.save(new ServiceType("Brake Inspection", "Inspect brake pads, rotors, and calipers", 2, new BigDecimal("149.99")));
    serviceTypeRepository.save(new ServiceType("Engine Tune-Up", "Replace spark plugs, air filter, and check engine components", 3, new BigDecimal("199.99")));
    serviceTypeRepository.save(new ServiceType("Transmission Service", "Flush and replace transmission fluid", 2, new BigDecimal("179.99")));
  }
}
