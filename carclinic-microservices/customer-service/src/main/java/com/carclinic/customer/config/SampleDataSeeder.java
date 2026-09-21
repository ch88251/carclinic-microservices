package com.carclinic.customer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.carclinic.customer.Customer;
import com.carclinic.customer.CustomerRepository;

/**
 * Seeds the database with sample customers and vehicles on startup, unless data
 * already exists or seeding is disabled via {@code app.seed.enabled=false}.
 */
@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class SampleDataSeeder implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleDataSeeder.class);

    private final CustomerRepository customerRepository;

    public SampleDataSeeder(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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

    }

}
