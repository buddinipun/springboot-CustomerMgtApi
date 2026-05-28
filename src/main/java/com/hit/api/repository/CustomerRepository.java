package com.hit.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.api.entity.Customer;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByNic(String nic);

    Optional<Customer> findByNic(String nic);

    long countByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );
}
