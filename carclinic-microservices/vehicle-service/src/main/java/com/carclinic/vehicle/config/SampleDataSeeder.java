package com.carclinic.vehicle.config;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.carclinic.vehicle.Vehicle;
import com.carclinic.vehicle.VehicleRepository;

/**
 * Seeds the database with sample vehicles on startup, unless data already exists or
 * seeding is disabled via {@code app.seed.enabled=false}. Customer IDs match the
 * sample customers seeded by customer-service's own SampleDataSeeder.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class SampleDataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleDataSeeder.class);

    private final VehicleRepository vehicleRepository;

    public SampleDataSeeder(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (vehicleRepository.count() > 0) {
            log.info("Skipping sample data seeding; vehicles already present");
            return;
        }

        log.info("Seeding sample vehicles");

        vehicleRepository.saveAll(List.of(
                vehicle("1HGCM82633A004352", "Honda", "Accord", "Blue", 2018, 42000, 1L),
                vehicle("2T1BURHE0JC014394", "Toyota", "Corolla", "Silver", 2020, 21000, 2L),
                vehicle("3VWFE21C04M000123", "Volkswagen", "Jetta", "Black", 2016, 78000, 3L),
                vehicle("1FTFW1ET1EFA00001", "Ford", "F-150", "Red", 2021, 9000, 3L)));
    }

    private Vehicle vehicle(String vin, String make, String model, String color, int year, int mileage,
            Long customerId) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(vin);
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setYear(year);
        vehicle.setMileage(mileage);
        vehicle.setLastServiceDate(LocalDate.now().minusMonths(3));
        vehicle.setNextServiceDate(LocalDate.now().plusMonths(3));
        vehicle.setCustomerId(customerId);
        return vehicle;
    }
}
