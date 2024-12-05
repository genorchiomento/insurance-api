package io.github.genorchiomento.insuranceapi.infrastructure.integration;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.exception.CustomerNotFoundException;
import io.github.genorchiomento.insuranceapi.application.exception.IntegrationException;
import io.github.genorchiomento.insuranceapi.infrastructure.integration.client.CustomerWithFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerApiIntegration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApiIntegration.class);

    private final CustomerWithFeignClient customerWithFeignClient;

    public CustomerApiIntegration(CustomerWithFeignClient customerWithFeignClient) {
        this.customerWithFeignClient = customerWithFeignClient;
    }

    public boolean customerExists(InsuranceRequest request) {
        try {
            var customer = customerWithFeignClient.getCustomerByCpf(request.cpf());
            LOGGER.info("Customer found: {}", customer);
            return customer != null;
        } catch (CustomerNotFoundException ex) {
            throw new CustomerNotFoundException("Customer not found with CPF: " + request.cpf());
        } catch (Exception ex) {
            LOGGER.error("Error verifying customer with CPF {}: {}", request.cpf(), ex.getMessage());
            throw new IntegrationException("Failed to connect to Customer API", ex);
        }
    }
}