package io.github.genorchiomento.insuranceapi.application.service;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.dto.InsuranceSimulationResponse;
import io.github.genorchiomento.insuranceapi.application.exception.CustomerNotFoundException;
import io.github.genorchiomento.insuranceapi.application.exception.InvalidRequestException;
import io.github.genorchiomento.insuranceapi.domain.model.Insurance;
import io.github.genorchiomento.insuranceapi.domain.model.InsuranceType;
import io.github.genorchiomento.insuranceapi.domain.repository.InsuranceRepository;
import io.github.genorchiomento.insuranceapi.infrastructure.integration.CustomerApiIntegration;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final CustomerApiIntegration customerApiIntegration;

    public InsuranceService(InsuranceRepository insuranceRepository, CustomerApiIntegration customerApiIntegration) {
        this.insuranceRepository = insuranceRepository;
        this.customerApiIntegration = customerApiIntegration;
    }

    public List<InsuranceSimulationResponse> simulateInsurance() {
        return Stream.of(InsuranceType.values())
                .map(type -> new InsuranceSimulationResponse(
                        type.name(),
                        type.getDescription(),
                        type.getMonthlyCost()
                ))
                .collect(Collectors.toList());
    }

    public Insurance contractInsurance(InsuranceRequest request) {
        validateInsuranceType(request.insuranceType());

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

    private void validateInsuranceType(String insuranceType) {
        try {
            InsuranceType.valueOf(insuranceType.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidRequestException("Invalid insurance type: " + insuranceType);
        }
    }
}
