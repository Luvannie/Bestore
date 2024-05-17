package com.luvannie.springbootbookecommerce.dao;

import com.luvannie.springbootbookecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
