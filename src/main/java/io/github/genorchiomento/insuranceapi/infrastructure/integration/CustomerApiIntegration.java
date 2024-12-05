package io.github.genorchiomento.insuranceapi.infrastructure.integration;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.infrastructure.integration.client.CustomerWithFeignClient;
import io.github.resilience4j.retry.annotation.Retry;
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

    @Retry(name = "default-retry")
    public boolean customerExists(InsuranceRequest request) {
        try {
            var customer = customerWithFeignClient.getCustomerByCpf(request.cpf());
            LOGGER.info("Customer found: {}", customer);
            return customer != null;
        } catch (Exception ex) {
            LOGGER.error("Error verifying customer with CPF {}: {}", request.cpf(), ex.getMessage());
            return false;
        }
    }
}