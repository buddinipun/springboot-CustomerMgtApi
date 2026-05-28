package com.hit.api.service;

import java.time.LocalDate;

import com.hit.api.dto.CustomerRequestDTO;
import com.hit.api.dto.CustomerResponseDTO;

public interface CustomerService {

    CustomerResponseDTO registerCustomer(
            CustomerRequestDTO requestDTO);

    CustomerResponseDTO getCustomerByNic(String nic);

    long getRegistrationCount(
            LocalDate startDate,
            LocalDate endDate);
}