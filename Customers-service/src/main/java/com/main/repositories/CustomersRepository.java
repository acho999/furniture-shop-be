package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Customer;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, String>{

}
