package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.OrderedProduct;

@Repository
public interface OrderedProductsRepository extends JpaRepository<OrderedProduct, String>{

}
