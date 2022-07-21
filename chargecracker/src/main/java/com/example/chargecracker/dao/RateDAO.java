package com.example.chargecracker.dao;

import com.example.chargecracker.dto.RateDto;
import com.example.chargecracker.model.Rate;

import java.util.List;

public interface RateDAO {
    List<Rate> getAll();

    Rate getById(Long id);

    List<RateDto> getByStationId(Long id);

    Boolean isRateExist(Long userId, Long stationId);

    void create(Rate rate);

    void delete(Long id);

    void update(Long id, Rate rate);
}
