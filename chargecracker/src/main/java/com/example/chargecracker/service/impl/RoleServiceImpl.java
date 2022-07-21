package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.RoleDAO;
import com.example.chargecracker.model.Role;
import com.example.chargecracker.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public Role getRole(Long id) {
        return roleDAO.getById(id);
    }

    @Override
    public Role getRole(String name) {
        return roleDAO.getByName(name);
    }

    @Override
    public List<Role> getRoles() {
        return roleDAO.getAll();
    }
}
