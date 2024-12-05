package io.github.genorchiomento.insuranceapi.application.controller;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.dto.InsuranceSimulationResponse;
import io.github.genorchiomento.insuranceapi.application.service.InsuranceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurances")
public class InsuranceController {

    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping("/simulate")
    public ResponseEntity<List<InsuranceSimulationResponse>> simulateInsurance() {
        return ResponseEntity.ok(insuranceService.simulateInsurance());
    }

    @PostMapping("/contract")
    public ResponseEntity<?> contractInsurance(@Valid @RequestBody InsuranceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(insuranceService.contractInsurance(request));
    }
}
