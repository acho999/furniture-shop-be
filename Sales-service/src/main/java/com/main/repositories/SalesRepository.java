package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Sale;

@Repository
public interface SalesRepository extends JpaRepository<Sale, String>{

}
