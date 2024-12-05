package io.github.genorchiomento.insuranceapi.application.service;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.dto.InsuranceSimulationResponse;
import io.github.genorchiomento.insuranceapi.domain.model.Insurance;
import io.github.genorchiomento.insuranceapi.domain.repository.InsuranceRepository;
import io.github.genorchiomento.insuranceapi.infrastructure.integration.CustomerApiIntegration;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final CustomerApiIntegration customerApiIntegration;

    public InsuranceService(InsuranceRepository insuranceRepository, CustomerApiIntegration customerApiIntegration) {
        this.insuranceRepository = insuranceRepository;
        this.customerApiIntegration = customerApiIntegration;
    }

    public List<InsuranceSimulationResponse> simulateInsurance() {
        return List.of(
                new InsuranceSimulationResponse(
                        "Bronze",
                        "Cobertura básica para danos e roubos.",
                        50.0),
                new InsuranceSimulationResponse(
                        "Silver",
                        "Cobertura intermediária," +
                                " incluindo desastres naturais.",
                        100.0),
                new InsuranceSimulationResponse(
                        "Gold",
                        "Cobertura completa e assistência 24h.",
                        150.0)
        );
    }

    public Insurance contractInsurance(InsuranceRequest request) {
        if (!customerApiIntegration.customerExists(request)) {
            throw new RuntimeException("Customer not found with CPF: " + request.cpf());
        }

        Insurance insurance = new Insurance(
                null,
                request.cpf(),
                request.insuranceType(),
                LocalDate.now().toString()
        );

        return insuranceRepository.save(insurance);
    }
}
