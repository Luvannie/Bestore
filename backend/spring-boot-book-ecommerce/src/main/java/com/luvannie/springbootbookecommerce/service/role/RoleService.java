package com.luvannie.springbootbookecommerce.service.role;

import com.luvannie.springbootbookecommerce.dao.RoleRepository;
import com.luvannie.springbootbookecommerce.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
