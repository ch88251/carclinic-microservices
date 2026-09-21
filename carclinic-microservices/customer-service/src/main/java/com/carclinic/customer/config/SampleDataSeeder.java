package com.carclinic.customer.config;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.carclinic.customer.model.Customer;
import com.carclinic.customer.model.CustomerRepository;
import com.carclinic.customer.model.Vehicle;
import com.carclinic.customer.model.VehicleRepository;

/**
 * Seeds the database with sample customers and vehicles on startup, unless data
 * already exists or seeding is disabled via {@code app.seed.enabled=false}.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class SampleDataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleDataSeeder.class);

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public SampleDataSeeder(CustomerRepository customerRepository, VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (customerRepository.count() > 0) {
            log.info("Skipping sample data seeding; customers already present");
            return;
        }

        log.info("Seeding sample customers and vehicles");

        Customer jane = customerRepository.save(new Customer("Jane", "Doe", "jane.doe@example.com", "555-0101"));
        Customer john = customerRepository.save(new Customer("John", "Smith", "john.smith@example.com", "555-0102"));
        Customer maria = customerRepository.save(new Customer("Maria", "Garcia", "maria.garcia@example.com", "555-0103"));

        vehicleRepository.saveAll(List.of(
                vehicle("1HGCM82633A004352", "Honda", "Accord", "Blue", 2018, 42000, jane),
                vehicle("2T1BURHE0JC014394", "Toyota", "Corolla", "Silver", 2020, 21000, john),
                vehicle("3VWFE21C04M000123", "Volkswagen", "Jetta", "Black", 2016, 78000, maria),
                vehicle("1FTFW1ET1EFA00001", "Ford", "F-150", "Red", 2021, 9000, maria)));
    }

    private Vehicle vehicle(String vin, String make, String model, String color, int year, int mileage,
            Customer owner) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(vin);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setYear(year);
        vehicle.setMileage(mileage);
        vehicle.setLastServiceDate(LocalDate.now().minusMonths(3));
        vehicle.setNextServiceDate(LocalDate.now().plusMonths(3));
        vehicle.setCustomer(owner);
        return vehicle;
    }
}
