package io.github.genorchiomento.insuranceapi.infrastructure.integration.client;

import io.github.genorchiomento.insuranceapi.infrastructure.integration.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customerApiClient",
        url = "${customer.client.integration.baseUrl}"
)
public interface CustomerWithFeignClient {

    @GetMapping("/{cpf}")
    CustomerResponse getCustomerByCpf(@PathVariable String cpf);
}