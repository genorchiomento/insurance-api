package io.github.genorchiomento.insuranceapi.infrastructure.config;

import feign.codec.ErrorDecoder;
import io.github.genorchiomento.insuranceapi.infrastructure.integration.interceptor.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}