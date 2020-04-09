package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{

}
