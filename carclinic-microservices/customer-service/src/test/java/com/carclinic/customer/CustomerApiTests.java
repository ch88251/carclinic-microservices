package com.carclinic.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end API tests that exercise the customer-service HTTP layer against a real
 * Postgres database (via Testcontainers), unlike the {@code CustomerResourceTests}
 * which mocks out the repository layer.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.seed.enabled=false"
})
@Transactional
class CustomerApiTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateAndFetchCustomer() throws Exception {
        String response = mockMvc.perform(post("/customers")
                .contentType("application/json")
                .content("{\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"email\":\"jane.smith@example.com\",\"phone\":\"555-0100\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.phone").value("555-0100"))
                .andReturn().getResponse().getContentAsString();

        long id = com.jayway.jsonpath.JsonPath.<Number>read(response, "$.id").longValue();

        mockMvc.perform(get("/customers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.phone").value("555-0100"));
    }

    @Test
    void shouldListCreatedCustomers() throws Exception {
        createCustomer("Alice", "Anderson", "alice@example.com", "555-0001");
        createCustomer("Bob", "Baker", "bob@example.com", "555-0002");

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnNotFoundForUnknownCustomer() throws Exception {
        mockMvc.perform(get("/customers/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateExistingCustomer() throws Exception {
        long id = createCustomer("Carl", "Carter", "carl@example.com", "555-0003");

        mockMvc.perform(put("/customers/" + id)
                .contentType("application/json")
                .content("{\"firstName\":\"Carl\",\"lastName\":\"Carter-Jones\",\"email\":\"carl.jones@example.com\",\"phone\":\"555-9999\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.lastName").value("Carter-Jones"))
                .andExpect(jsonPath("$.email").value("carl.jones@example.com"));

        mockMvc.perform(get("/customers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Carter-Jones"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingUnknownCustomer() throws Exception {
        mockMvc.perform(put("/customers/999999")
                .contentType("application/json")
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"123-456-7890\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteExistingCustomer() throws Exception {
        long id = createCustomer("Dana", "Dawson", "dana@example.com", "555-0004");

        mockMvc.perform(delete("/customers/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/customers/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingUnknownCustomer() throws Exception {
        mockMvc.perform(delete("/customers/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRejectCustomerWithMissingRequiredFields() throws Exception {
        mockMvc.perform(post("/customers")
                .contentType("application/json")
                .content("{\"firstName\":\"\",\"lastName\":\"\",\"email\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    private long createCustomer(String firstName, String lastName, String email, String phone) throws Exception {
        String response = mockMvc.perform(post("/customers")
                .contentType("application/json")
                .content("{\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\",\"email\":\"" + email + "\",\"phone\":\"" + phone + "\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return com.jayway.jsonpath.JsonPath.<Number>read(response, "$.id").longValue();
    }
}
