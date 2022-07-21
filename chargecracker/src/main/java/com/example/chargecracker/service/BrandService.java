package com.example.chargecracker.service;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getBrands();

    Brand getBrand(Long id);

    void createBrand(Brand brand) throws ModelAlreadyExistsException, ModelAttributeException;

    void deleteBrand(Long id) throws ModelNotFoundException;

    void updateBrand(Long id, Brand brand) throws ModelNotFoundException, ModelAttributeException, ModelAlreadyExistsException;

}
