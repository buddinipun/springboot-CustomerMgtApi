package com.hit.api.controller;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hit.api.dto.CustomerRequestDTO;
import com.hit.api.dto.CustomerResponseDTO;
import com.hit.api.service.CustomerService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Register new customer")
    @PostMapping
    public ResponseEntity<CustomerResponseDTO>
    registerCustomer(
            @Valid @RequestBody
            CustomerRequestDTO requestDTO) {

        CustomerResponseDTO response =
                customerService.registerCustomer(
                        requestDTO);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Operation(summary = "Search customer by NIC")
    @GetMapping("/{nic}")
    public ResponseEntity<CustomerResponseDTO>
    getCustomerByNic(
            @PathVariable String nic) {

        return ResponseEntity.ok(
                customerService.getCustomerByNic(nic));
    }

    @Operation(summary =
            "Get customer registration statistics")
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>>
    getStatistics(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        long count =
                customerService.getRegistrationCount(
                        startDate,
                        endDate);

        return ResponseEntity.ok(
                Map.of("registrationCount", count));
    }
}
