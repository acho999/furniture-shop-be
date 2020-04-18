package com.main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.models.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long>{

}
