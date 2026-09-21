package com.carclinic.customer;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.carclinic.customer.mapper.CustomerEntityMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(CustomerResource.class)
public class CustomerResourceTests {
    
    @Autowired
    MockMvc mockMvc;

    @MockitoBean 
    CustomerRepository customerRepository;

    @MockitoBean
    CustomerEntityMapper customerEntityMapper;

    @Test 
    void shouldGetCustomer() throws Exception {
        Customer customer = createCustomer();
        given(customerRepository.findById(customer.getId())).willReturn(java.util.Optional.of(customer));

        mockMvc.perform(get("/customers/" + customer.getId()))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(customer.getId()))
               .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
               .andExpect(jsonPath("$.email").value(customer.getEmail()))
               .andExpect(jsonPath("$.phone").value(customer.getPhone()));
    }

    @Test
    void shouldNotGetCustomerWithInvalidId() throws Exception {
        given(customerRepository.findById(999L)).willReturn(java.util.Optional.empty());

        mockMvc.perform(get("/customers/999"))
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllCustomers() throws Exception {
        Customer customer = createCustomer();
        given(customerRepository.findAll()).willReturn(java.util.List.of(customer));

        mockMvc.perform(get("/customers"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(customer.getId()))
               .andExpect(jsonPath("$[0].firstName").value(customer.getFirstName()))
               .andExpect(jsonPath("$[0].lastName").value(customer.getLastName()))
               .andExpect(jsonPath("$[0].email").value(customer.getEmail()))
               .andExpect(jsonPath("$[0].phone").value(customer.getPhone()));
    }

    @Test
    void shouldGetNoCustomers() throws Exception {
        given(customerRepository.findAll()).willReturn(java.util.List.of());

        mockMvc.perform(get("/customers"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldCreateCustomer() throws Exception {
        Customer customer = createCustomer();
        given(customerEntityMapper.map(any(Customer.class), any(CustomerRequest.class))).willReturn(customer);
        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        mockMvc.perform(post("/customers")
               .contentType("application/json")
               .content("{\"firstName\":\"" + customer.getFirstName() + "\",\"lastName\":\"" + customer.getLastName() + "\",\"email\":\"" + customer.getEmail() + "\",\"phone\":\"" + customer.getPhone() + "\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(customer.getId()))
               .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
               .andExpect(jsonPath("$.email").value(customer.getEmail()))
               .andExpect(jsonPath("$.phone").value(customer.getPhone()));
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        Customer customer = createCustomer();
        given(customerRepository.findById(customer.getId())).willReturn(java.util.Optional.of(customer));
        given(customerEntityMapper.map(any(Customer.class), any(CustomerRequest.class))).willReturn(customer);
        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        mockMvc.perform(put("/customers/" + customer.getId())
               .contentType("application/json")
               .content("{\"firstName\":\"" + customer.getFirstName() + "\",\"lastName\":\"" + customer.getLastName() + "\",\"email\":\"" + customer.getEmail() + "\",\"phone\":\"" + customer.getPhone() + "\"}"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(customer.getId()))
               .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
               .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
               .andExpect(jsonPath("$.email").value(customer.getEmail()))
               .andExpect(jsonPath("$.phone").value(customer.getPhone()));
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        Customer customer = createCustomer();
         given(customerRepository.existsById(customer.getId())).willReturn(true);

        mockMvc.perform(delete("/customers/" + customer.getId()))
             .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingCustomer() throws Exception {
        given(customerRepository.existsById(1L)).willReturn(false);

        mockMvc.perform(delete("/customers/1"))
             .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenGettingNonExistingCustomer() throws Exception {
        given(customerRepository.findById(1L)).willReturn(java.util.Optional.empty());

        mockMvc.perform(get("/customers/1"))
             .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingCustomer() throws Exception {
        given(customerRepository.findById(1L)).willReturn(java.util.Optional.empty());

        mockMvc.perform(put("/customers/1")
               .contentType("application/json")
               .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"123-456-7890\"}"))
               .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenCreatingCustomerWithInvalidData() throws Exception {
        mockMvc.perform(post("/customers")
               .contentType("application/json")
               .content("{\"firstName\":\"\",\"lastName\":\"\",\"email\":\"invalid-email\",\"phone\":\"\"}"))
               .andExpect(status().isBadRequest());
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("123-456-7890");
        return customer;
    }
}
