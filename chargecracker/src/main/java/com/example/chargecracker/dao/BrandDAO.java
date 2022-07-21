package com.example.chargecracker.dao;

import com.example.chargecracker.model.Brand;

import java.util.List;

public interface BrandDAO {
    List<Brand> getAll();

    Brand getById(Long id);

    Brand getByName(String name);

    void create(Brand brand);

    void delete(Long id);

    void update(Long id, Brand brand);
}
