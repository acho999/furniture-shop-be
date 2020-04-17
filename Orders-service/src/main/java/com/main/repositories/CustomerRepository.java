package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{

}
