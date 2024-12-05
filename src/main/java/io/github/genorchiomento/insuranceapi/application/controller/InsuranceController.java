package io.github.genorchiomento.insuranceapi.application.controller;

import io.github.genorchiomento.insuranceapi.application.dto.InsuranceRequest;
import io.github.genorchiomento.insuranceapi.application.dto.InsuranceSimulationResponse;
import io.github.genorchiomento.insuranceapi.application.service.InsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurances")
@Tag(name = "Insurance", description = "Insurance management APIs")
@Validated
public class InsuranceController {

    private final InsuranceService insuranceService;

    public InsuranceController(final InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping("/simulate")
    @Operation(summary = "Simulate insurance options", description = "Provides a list of available insurance options with descriptions and costs")
    @ApiResponse(responseCode = "200", description = "Simulation retrieved successfully")
    public ResponseEntity<List<InsuranceSimulationResponse>> simulateInsurance() {
        List<InsuranceSimulationResponse> response = insuranceService.simulateInsurance();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/contract")
    @Operation(summary = "Contract an insurance", description = "Contracts an insurance for an existing customer by CPF")
    @ApiResponse(responseCode = "201", description = "Insurance contracted successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<?> contractInsurance(@Valid @RequestBody InsuranceRequest request) {
        var response = insuranceService.contractInsurance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}