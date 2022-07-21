package com.example.chargecracker.service;

import com.example.chargecracker.dto.RateDto;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Rate;

import java.util.List;

public interface RateService {
    List<Rate> getRates();

    Rate getRate(Long id);

    List<RateDto> getRatesStation(Long id);

    void createRate(Rate rate) throws ModelAttributeException, ModelNotFoundException, ModelAlreadyExistsException;

    void deleteRate(Long id) throws ModelNotFoundException;

    void updateRate(Long id, Rate rate) throws ModelNotFoundException, ModelAttributeException;
}
