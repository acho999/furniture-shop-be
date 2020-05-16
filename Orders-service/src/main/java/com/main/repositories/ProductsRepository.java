package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, String>{

}
