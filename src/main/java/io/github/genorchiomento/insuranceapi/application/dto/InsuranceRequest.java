package io.github.genorchiomento.insuranceapi.application.dto;

import jakarta.validation.constraints.NotBlank;

public record InsuranceRequest(
        @NotBlank(message = "CPF is mandatory") String cpf,
        @NotBlank(message = "Insurance type is mandatory") String insuranceType
) {}