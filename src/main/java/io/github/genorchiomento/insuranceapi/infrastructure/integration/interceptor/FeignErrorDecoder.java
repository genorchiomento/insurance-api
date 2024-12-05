package io.github.genorchiomento.insuranceapi.infrastructure.integration.interceptor;


import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.genorchiomento.insuranceapi.application.exception.InternalServerErrorException;
import io.github.genorchiomento.insuranceapi.application.exception.ResourceNotFoundException;
import io.github.genorchiomento.insuranceapi.application.exception.TooManyRequestsException;
import org.apache.coyote.BadRequestException;

import java.util.concurrent.TimeoutException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 408, 504 -> new TimeoutException("Request timeout");
            case 400 -> new BadRequestException("Invalid request parameters");
            case 404 -> new ResourceNotFoundException("Resource not found");
            case 429 -> new TooManyRequestsException("Rate limit exceeded");
            default -> new InternalServerErrorException("Integration error: " + response);
        };
    }
}