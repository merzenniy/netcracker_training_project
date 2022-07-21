package com.example.chargecracker.dao;

import com.example.chargecracker.model.Auto;

import java.util.List;

public interface AutoDAO {
    List<Auto> getAll();

    Auto getById(Long id);

    Long create(Auto auto);

    void delete(Long id);

    void update(Long id, Auto auto);
}
