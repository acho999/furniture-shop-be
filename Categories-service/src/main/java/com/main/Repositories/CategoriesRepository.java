package com.main.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<String, Category>{

}
