package io.github.genorchiomento.insuranceapi.domain.model;

public enum InsuranceType {
    BRONZE("Cobertura básica para danos e roubos.", 50.0),
    SILVER("Cobertura intermediária, incluindo desastres naturais.", 100.0),
    GOLD("Cobertura completa e assistência 24h.", 150.0);

    private final String description;
    private final double monthlyCost;

    InsuranceType(String description, double monthlyCost) {
        this.description = description;
        this.monthlyCost = monthlyCost;
    }

    public String getDescription() {
        return description;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }
}
