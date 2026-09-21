package com.carclinic.vehicle;

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
 * End-to-end API tests that exercise the vehicle-service HTTP layer against a real
 * Postgres database (via Testcontainers), unlike the {@code VehicleResourceTests}
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
class VehicleApiTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldCreateAndFetchVehicle() throws Exception {
        String response = mockMvc.perform(post("/vehicles")
                .contentType("application/json")
                .content("{\"vin\":\"1HGCM82633A004352\",\"make\":\"Honda\",\"model\":\"Accord\",\"color\":\"Blue\",\"year\":2018,\"mileage\":42000,\"customerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.vin").value("1HGCM82633A004352"))
                .andExpect(jsonPath("$.make").value("Honda"))
                .andExpect(jsonPath("$.customerId").value(1))
                .andReturn().getResponse().getContentAsString();

        long id = com.jayway.jsonpath.JsonPath.<Number>read(response, "$.id").longValue();

        mockMvc.perform(get("/vehicles/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.vin").value("1HGCM82633A004352"));
    }

    @Test
    void shouldListCreatedVehicles() throws Exception {
        createVehicle("1FTFW1ET1EFA00001", "Ford", "F-150", 2L);
        createVehicle("3VWFE21C04M000123", "Volkswagen", "Jetta", 3L);

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnNotFoundForUnknownVehicle() throws Exception {
        mockMvc.perform(get("/vehicles/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateExistingVehicle() throws Exception {
        long id = createVehicle("2T1BURHE0JC014394", "Toyota", "Corolla", 2L);

        mockMvc.perform(put("/vehicles/" + id)
                .contentType("application/json")
                .content("{\"vin\":\"2T1BURHE0JC014394\",\"make\":\"Toyota\",\"model\":\"Corolla\",\"color\":\"Red\",\"year\":2020,\"mileage\":25000,\"customerId\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.color").value("Red"));

        mockMvc.perform(get("/vehicles/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingUnknownVehicle() throws Exception {
        mockMvc.perform(put("/vehicles/999999")
                .contentType("application/json")
                .content("{\"vin\":\"1FTFW1ET1EFA00001\",\"make\":\"Ford\",\"model\":\"F-150\",\"color\":\"Red\",\"year\":2021,\"mileage\":9000,\"customerId\":1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteExistingVehicle() throws Exception {
        long id = createVehicle("1HGCM82633A004352", "Honda", "Accord", 1L);

        mockMvc.perform(delete("/vehicles/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/vehicles/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingUnknownVehicle() throws Exception {
        mockMvc.perform(delete("/vehicles/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRejectVehicleWithMissingRequiredFields() throws Exception {
        mockMvc.perform(post("/vehicles")
                .contentType("application/json")
                .content("{\"vin\":\"\",\"make\":\"\",\"model\":\"\",\"color\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    private long createVehicle(String vin, String make, String model, Long customerId) throws Exception {
        String response = mockMvc.perform(post("/vehicles")
                .contentType("application/json")
                .content("{\"vin\":\"" + vin + "\",\"make\":\"" + make + "\",\"model\":\"" + model
                        + "\",\"color\":\"Black\",\"year\":2020,\"mileage\":10000,\"customerId\":" + customerId + "}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return com.jayway.jsonpath.JsonPath.<Number>read(response, "$.id").longValue();
    }
}
