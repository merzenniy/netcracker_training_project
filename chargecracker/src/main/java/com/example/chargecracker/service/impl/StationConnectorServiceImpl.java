package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.StationConnectorDao;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.StationConnector;
import com.example.chargecracker.service.StationConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationConnectorServiceImpl implements StationConnectorService {
    @Autowired
    private StationConnectorDao stationConnectorDao;

    @Override
    public List<StationConnector> getStationsConnectors() {
        return stationConnectorDao.getAll();
    }

    @Override
    public StationConnector getStationConnector(Long id) {
        return stationConnectorDao.getById(id);
    }

    @Override
    public void createStationConnector(StationConnector stationConnector) throws ModelAttributeException {
        validationStationConnector(stationConnector);
        stationConnectorDao.create(stationConnector);
    }

    @Override
    public void deleteStationConnector(Long id) throws ModelNotFoundException {
        if (stationConnectorDao.getById(id) == null) {
            throw new ModelNotFoundException("StationConnector with id " + id + " not found");
        }
        stationConnectorDao.delete(id);
    }

    @Override
    public void updateStationConnector(Long id, StationConnector stationConnector)
            throws ModelAttributeException, ModelNotFoundException {
        if (stationConnectorDao.getById(id) == null) {
            throw new ModelNotFoundException("StationConnector with id " + id + " not found");
        }
        validationStationConnector(stationConnector);
        stationConnectorDao.update(id, stationConnector);
    }

    private void validationStationConnector(StationConnector stationConnector) throws ModelAttributeException {
        if (stationConnector.getStationId() == null || stationConnector.getConnectorId() == null) {
            throw new ModelAttributeException("Не правильно введенные данные");
        }
    }
}
