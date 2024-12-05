package io.github.genorchiomento.insuranceapi.application.controller;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.dto.InsuranceSimulationResponse;
import io.github.genorchiomento.insuranceapi.infrastructure.integration.CustomerApiIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InsuranceControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CustomerApiIntegration customerApiIntegration;

    @Test
    void simulateInsurance_shouldReturnInsuranceOptions() {
        ResponseEntity<InsuranceSimulationResponse[]> response =
                restTemplate.getForEntity("/api/insurances/simulate", InsuranceSimulationResponse[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);

        InsuranceSimulationResponse bronzeInsurance = response.getBody()[0];
        assertEquals("BRONZE", bronzeInsurance.type());
        assertEquals("Cobertura básica para danos e roubos.", bronzeInsurance.description());
        assertEquals(50.0, bronzeInsurance.monthlyCost());
    }

    @Test
    void contractInsurance_shouldReturnCreatedStatusWhenCustomerExists() {
        InsuranceRequest validRequest = new InsuranceRequest("12345678900", "BRONZE");
        when(customerApiIntegration.customerExists(validRequest)).thenReturn(true);

        ResponseEntity<?> response = restTemplate.postForEntity("/api/insurances/contract", validRequest, Object.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(customerApiIntegration, times(1)).customerExists(validRequest);
    }

    @Test
    void contractInsurance_shouldReturnNotFoundWhenCustomerDoesNotExist() {
        InsuranceRequest invalidRequest = new InsuranceRequest("00000000000", "BRONZE");
        when(customerApiIntegration.customerExists(invalidRequest)).thenReturn(false);

        ResponseEntity<?> response = restTemplate.postForEntity("/api/insurances/contract", invalidRequest, Object.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(customerApiIntegration, times(1)).customerExists(invalidRequest);
    }


    @Test
    void contractInsurance_shouldReturnBadRequestForInvalidInsuranceType() {
        InsuranceRequest invalidRequest = new InsuranceRequest("12345678900", "INVALID_TYPE");

        ResponseEntity<?> response = restTemplate.postForEntity("/api/insurances/contract", invalidRequest, Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(customerApiIntegration, times(0)).customerExists(invalidRequest);
    }
}
