package io.github.genorchiomento.insuranceapi.application.service;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.dto.InsuranceSimulationResponse;
import io.github.genorchiomento.insuranceapi.application.exception.CustomerNotFoundException;
import io.github.genorchiomento.insuranceapi.application.exception.InvalidRequestException;
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
                new InsuranceSimulationResponse("Bronze", "Cobertura básica para danos e roubos.", 50.0),
                new InsuranceSimulationResponse("Prata", "Cobertura intermediária, incluindo desastres naturais.", 100.0),
                new InsuranceSimulationResponse("Ouro", "Cobertura completa e assistência 24h.", 150.0)
        );
    }

    public Insurance contractInsurance(InsuranceRequest request) {
        validateRequest(request);

        if (!customerApiIntegration.customerExists(request)) {
            throw new CustomerNotFoundException("Customer not found with CPF: " + request.cpf());
        }

        Insurance insurance = new Insurance(
                null,
                request.cpf(),
                request.insuranceType(),
                LocalDate.now().toString()
        );

        return insuranceRepository.save(insurance);
    }

    private void validateRequest(InsuranceRequest request) {
        if (request.insuranceType() == null || request.insuranceType().isBlank()) {
            throw new InvalidRequestException("Insurance type is mandatory");
        }
        if (request.cpf() == null || request.cpf().isBlank()) {
            throw new InvalidRequestException("CPF is mandatory");
        }
    }
}
