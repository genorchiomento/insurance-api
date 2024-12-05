package io.github.genorchiomento.insuranceapi.application.dto;

public record InsuranceSimulationResponse(
        String type,
        String description,
        double monthlyCost
) {}
