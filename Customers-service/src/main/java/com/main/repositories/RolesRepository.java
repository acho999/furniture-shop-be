package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.models.Role;

public interface RolesRepository extends JpaRepository<Role, Long>{

}
