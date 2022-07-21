package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.BrandDAO;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Brand;
import com.example.chargecracker.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDAO brandDAO;

    @Override
    public List<Brand> getBrands() {
        return brandDAO.getAll();
    }

    @Override
    public Brand getBrand(Long id) {
        return brandDAO.getById(id);
    }

    @Override
    public void createBrand(Brand brand) throws ModelAttributeException, ModelAlreadyExistsException {
        if (brandDAO.getByName(brand.getName()) != null) {
            throw new ModelAlreadyExistsException("Бренд с именем " + brand.getName() + " уже существует");
        }
        validationBrand(brand);
        brandDAO.create(brand);
    }

    @Override
    public void deleteBrand(Long id) throws ModelNotFoundException {
        if (brandDAO.getById(id) == null) {
            throw new ModelNotFoundException("Brand with id " + id + " not found");
        }
        brandDAO.delete(id);
    }

    @Override
    public void updateBrand(Long id, Brand brand) throws ModelNotFoundException, ModelAttributeException, ModelAlreadyExistsException {
        validationBrand(brand);
        Brand updatedBrand = brandDAO.getById(id);
        if (updatedBrand == null) {
            throw new ModelNotFoundException("Brand with id " + id + " not found");
        }
        Brand foundBrandByName = brandDAO.getByName(brand.getName());
        if (foundBrandByName != null) {
            if (!foundBrandByName.getName().equals(updatedBrand.getName())) {
                throw new ModelAlreadyExistsException("Бренд с именем " + brand.getName() + " уже существует");
            }
        } else {
            brandDAO.update(id, brand);
        }
    }

    private void validationBrand(Brand brand) throws ModelAttributeException {
        if (brand.getName() == null || brand.getName().isEmpty()) {
            throw new ModelAttributeException("Не правильно введенные данные");
        }
    }
}
