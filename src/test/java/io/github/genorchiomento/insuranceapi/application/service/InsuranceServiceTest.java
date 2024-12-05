package io.github.genorchiomento.insuranceapi.application.service;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.dto.InsuranceSimulationResponse;
import io.github.genorchiomento.insuranceapi.application.exception.InvalidRequestException;
import io.github.genorchiomento.insuranceapi.domain.model.Insurance;
import io.github.genorchiomento.insuranceapi.domain.repository.InsuranceRepository;
import io.github.genorchiomento.insuranceapi.infrastructure.integration.CustomerApiIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private CustomerApiIntegration customerApiIntegration;

    @InjectMocks
    private InsuranceService insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void simulateInsurance_shouldReturnAllInsuranceTypes() {
        List<InsuranceSimulationResponse> response = insuranceService.simulateInsurance();

        assertNotNull(response);
        assertEquals(3, response.size());
        assertEquals("BRONZE", response.get(0).type());
        assertEquals("Cobertura básica para danos e roubos.", response.get(0).description());
        assertEquals(50.0, response.get(0).monthlyCost());
    }

    @Test
    void contractInsurance_shouldSaveInsuranceWhenCustomerExists() {
        InsuranceRequest request = new InsuranceRequest("12345678900", "BRONZE");

        when(customerApiIntegration.customerExists(request)).thenReturn(true);
        when(insuranceRepository.save(any(Insurance.class))).thenReturn(
                new Insurance("1", "12345678900", "BRONZE", "2024-12-05")
        );

        Insurance response = insuranceService.contractInsurance(request);

        assertNotNull(response);
        assertEquals("12345678900", response.cpf());
        assertEquals("BRONZE", response.insuranceType());
        verify(customerApiIntegration, times(1)).customerExists(request);
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void contractInsurance_shouldThrowExceptionWhenCustomerNotFound() {
        InsuranceRequest request = new InsuranceRequest("12345678900", "BRONZE");

        when(customerApiIntegration.customerExists(request)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            insuranceService.contractInsurance(request);
        });

        assertEquals("Customer not found with CPF: 12345678900", exception.getMessage());
        verify(customerApiIntegration, times(1)).customerExists(request);
        verify(insuranceRepository, times(0)).save(any(Insurance.class));
    }

    @Test
    void contractInsurance_shouldThrowExceptionForInvalidInsuranceType() {
        InsuranceRequest request = new InsuranceRequest("12345678900", "INVALID_TYPE");

        Exception exception = assertThrows(InvalidRequestException.class, () -> {
            insuranceService.contractInsurance(request);
        });

        assertEquals("Invalid insurance type: INVALID_TYPE", exception.getMessage());
        verify(customerApiIntegration, times(0)).customerExists(request);
        verify(insuranceRepository, times(0)).save(any(Insurance.class));
    }
}
