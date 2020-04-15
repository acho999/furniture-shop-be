package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, String>{

}
