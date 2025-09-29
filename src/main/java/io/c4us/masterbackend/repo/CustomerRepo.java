package io.c4us.masterbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.Customer;

public interface CustomerRepo extends JpaRepository<Customer, String>{
    
    Optional<Customer> findById(String id);
    Optional<Customer> findBynumCust(String numcust);
}
