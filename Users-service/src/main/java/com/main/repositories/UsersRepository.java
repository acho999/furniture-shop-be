package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Admin;

@Repository
public interface UsersRepository extends JpaRepository<Admin, Long>{

}
