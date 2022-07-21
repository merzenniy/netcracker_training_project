package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.RateDAO;
import com.example.chargecracker.dto.RateDto;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Rate;
import com.example.chargecracker.service.RateService;
import com.example.chargecracker.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateDAO rateDAO;
    @Autowired
    private StationService stationService;

    @Override
    public List<Rate> getRates() {
        return rateDAO.getAll();
    }

    @Override
    public Rate getRate(Long id) {
        return rateDAO.getById(id);
    }

    @Override
    public List<RateDto> getRatesStation(Long id) {
        return rateDAO.getByStationId(id);
    }

    @Override
    public void createRate(Rate rate) throws ModelAttributeException, ModelNotFoundException, ModelAlreadyExistsException {
        if (rateDAO.isRateExist(rate.getUserId(), rate.getStationId())) {
            throw new ModelAlreadyExistsException("Вы уже оставили отзыв на эту станцию");
        }
        validationRate(rate);
        rateDAO.create(rate);
        stationService.updateStationRate(rate.getStationId());
    }

    @Override
    public void deleteRate(Long id) throws ModelNotFoundException {
        Rate rate = rateDAO.getById(id);
        if (rate == null) {
            throw new ModelNotFoundException("Rate with id " + id + " not found");
        }
        rateDAO.delete(id);
        stationService.updateStationRate(rate.getStationId());
    }

    @Override
    public void updateRate(Long id, Rate rate) throws ModelNotFoundException, ModelAttributeException {
        Rate deletedRate = rateDAO.getById(id);
        if (deletedRate == null) {
            throw new ModelNotFoundException("Rate with id " + id + " not found");
        }
        validationRate(rate);
        rateDAO.update(id, rate);
        stationService.updateStationRate(deletedRate.getStationId());
    }

    private void validationRate(Rate rate) throws ModelAttributeException {
        if (rate.getStationId() < 0 || rate.getUserId() < 0 || (rate.getRate() < 0 && rate.getRate() > 5)
                || rate.getCommentary() == null) {
            throw new ModelAttributeException("Не правильно введенные данные");
        }
    }
}
