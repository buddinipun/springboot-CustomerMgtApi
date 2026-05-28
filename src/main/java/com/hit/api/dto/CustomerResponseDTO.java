package com.hit.api.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {

    private Long id;

    private String nic;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String address;

    private LocalDate dateOfBirth;

    private LocalDateTime createdAt;
}