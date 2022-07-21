package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.AutoDAO;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Auto;
import com.example.chargecracker.service.AutoService;
import com.example.chargecracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoServiceImpl implements AutoService {
    @Autowired
    private AutoDAO autoDAO;
    @Autowired
    private UserService userService;

    @Override
    public List<Auto> getAutos() {
        return autoDAO.getAll();
    }

    @Override
    public Auto getAuto(Long id) {
        return autoDAO.getById(id);
    }

    @Override
    public void createAuto(Auto auto) throws ModelAttributeException {
        validationAuto(auto);
        autoDAO.create(auto);
    }

    @Override
    public void createUserAuto(Long userId, Auto auto) throws ModelAttributeException {
        validationAuto(auto);
        Long id = autoDAO.create(auto);
        userService.updateUserAuto(userId, id);
    }

    @Override
    public void deleteAuto(Long id) throws ModelNotFoundException {
        if (autoDAO.getById(id) == null) {
            throw new ModelNotFoundException("Auto with id " + id + " not found");
        }
        autoDAO.delete(id);
    }

    @Override
    public void updateAuto(Long id, Auto auto) throws ModelNotFoundException, ModelAttributeException {
        if (autoDAO.getById(id) == null) {
            throw new ModelNotFoundException("Auto with id " + id + " not found");
        }
        validationAuto(auto);
        autoDAO.update(id, auto);
    }

    private void validationAuto(Auto auto) throws ModelAttributeException {
        if (auto.getBrandId() <= 0 || auto.getModel() == null || auto.getModel().isEmpty()
                || auto.getConnectorId() <= 0 || auto.getMaxCharge() <= 0) {
            throw new ModelAttributeException("Неправильно введенные данные");
        }
    }
}
