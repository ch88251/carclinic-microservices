package com.carclinic.customer.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.carclinic.customer.model.Customer;
import com.carclinic.customer.model.CustomerRepository;
import com.carclinic.customer.web.mapper.CustomerEntityMapper;

@RequestMapping("/customers")
@RestController 
public class CustomerResource {
    private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    public CustomerResource(CustomerRepository customerRepository, CustomerEntityMapper customerEntityMapper) {
        this.customerRepository = customerRepository;
        this.customerEntityMapper = customerEntityMapper;
    }

    /**
     * Create a new customer.
     * @param request the customer creation request
     * @return the created customer
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerRequest request) {
        log.info("Creating a new customer with request: {}", request);
        Customer customer = customerEntityMapper.map(new Customer(), request);
        customer = customerRepository.save(customer);
        log.info("Created a new customer: {}", customer);
        return ResponseEntity.ok(customer);
    }
    
    /**
     * Get a customer by ID.
     * @param id the ID of the customer
     * @return the customer with the given ID, or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        log.info("Fetching customer with id: {}", id);
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all customers.
     * @return a list of all customers
     */
    @GetMapping
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        log.info("Fetching all customers");
        Iterable<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    /**
     * Delete a customer by ID.
     * @param id the ID of the customer to delete
     * @return a 204 No Content response if the customer was deleted, or a 404 Not Found response if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.info("Deleting customer with id: {}", id);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update a customer by ID.
     * @param id the ID of the customer to update
     * @param request the customer update request
     * @return the updated customer, or a 404 Not Found response if the customer does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest request) {
        log.info("Updating customer with id: {}", id);
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    Customer updatedCustomer = customerEntityMapper.map(existingCustomer, request);
                    updatedCustomer = customerRepository.save(updatedCustomer);
                    return ResponseEntity.ok(updatedCustomer);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
