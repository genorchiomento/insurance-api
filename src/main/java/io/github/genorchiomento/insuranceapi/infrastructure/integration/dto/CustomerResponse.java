package io.github.genorchiomento.insuranceapi.infrastructure.integration.dto;

public record CustomerResponse(
        String id,
        String cpf,
        String name,
        String birthDate,
        String phone,
        String address
) {}

