package io.github.genorchiomento.insuranceapi.infrastructure.integration.interceptor;


import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.genorchiomento.insuranceapi.application.exception.*;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;

public class FeignErrorDecoder implements ErrorDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        LOGGER.error("Error in Feign client. Method: {}, Status: {}, Reason: {}",
                methodKey, response.status(), response.reason());

        switch (response.status()) {
            case 404:
                return new CustomerNotFoundException("Customer not found for the requested CPF");
            case 500:
                return new IntegrationException("Internal error in Customer API");
            case 503:
                return new IntegrationException("Customer API is currently unavailable");
            default:
                return new IntegrationException("Unexpected error occurred during communication with Customer API");
        }
    }
}