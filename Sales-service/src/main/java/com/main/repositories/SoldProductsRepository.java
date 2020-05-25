package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.SoldProduct;

@Repository
public interface SoldProductsRepository extends JpaRepository<SoldProduct, String>{

}
