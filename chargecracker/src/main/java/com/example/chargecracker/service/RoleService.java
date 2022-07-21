package com.example.chargecracker.service;

import com.example.chargecracker.model.Role;

import java.util.List;

public interface RoleService {
    Role getRole(Long id);

    Role getRole(String name);

    List<Role> getRoles();
}
