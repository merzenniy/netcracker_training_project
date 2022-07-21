package com.example.chargecracker.service;

import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Auto;

import java.util.List;

public interface AutoService {
    List<Auto> getAutos();

    Auto getAuto(Long id);

    void createAuto(Auto auto) throws ModelAttributeException;

    void createUserAuto(Long userId, Auto auto) throws ModelAttributeException;

    void deleteAuto(Long id) throws ModelNotFoundException;

    void updateAuto(Long id, Auto auto) throws ModelNotFoundException, ModelAttributeException;
}
