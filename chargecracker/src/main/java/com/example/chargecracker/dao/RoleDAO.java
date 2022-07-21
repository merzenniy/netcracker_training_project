package com.example.chargecracker.dao;

import com.example.chargecracker.model.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getAll();

    Role getById(Long id);

    Role getByName(String name);
}
