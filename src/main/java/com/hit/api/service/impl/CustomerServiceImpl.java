package com.hit.api.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.hit.api.dto.CustomerRequestDTO;
import com.hit.api.dto.CustomerResponseDTO;
import com.hit.api.entity.Customer;
import com.hit.api.exception.CustomerAlreadyExistsException;
import com.hit.api.exception.ResourceNotFoundException;
import com.hit.api.repository.CustomerRepository;
import com.hit.api.service.CustomerService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO registerCustomer(
            CustomerRequestDTO requestDTO) {

        log.info("Customer registration started for NIC: {}",
                requestDTO.getNic());

        if (customerRepository.existsByNic(requestDTO.getNic())) {

            log.error("Duplicate NIC detected: {}",
                    requestDTO.getNic());

            throw new CustomerAlreadyExistsException(
                    "Customer already registered with NIC: "
                            + requestDTO.getNic());
        }

        Customer customer = Customer.builder()
                .nic(requestDTO.getNic())
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .mobile(requestDTO.getMobile())
                .address(requestDTO.getAddress())
                .dateOfBirth(requestDTO.getDateOfBirth())
                .build();

        Customer savedCustomer =
                customerRepository.save(customer);

        log.info("Customer registered successfully. ID: {}",
                savedCustomer.getId());

        return mapToResponse(savedCustomer);
    }

    @Override
    public CustomerResponseDTO getCustomerByNic(String nic) {

        log.info("Searching customer by NIC: {}", nic);

        Customer customer = customerRepository.findByNic(nic)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found"));

        return mapToResponse(customer);
    }

    @Override
    public long getRegistrationCount(
            LocalDate startDate,
            LocalDate endDate) {

        log.info("Fetching registration statistics");

        return customerRepository.countByCreatedAtBetween(
                startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59));
    }

    private CustomerResponseDTO mapToResponse(
            Customer customer) {

        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .nic(customer.getNic())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .address(customer.getAddress())
                .dateOfBirth(customer.getDateOfBirth())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
