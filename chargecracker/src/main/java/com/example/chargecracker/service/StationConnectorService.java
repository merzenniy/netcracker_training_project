package com.example.chargecracker.service;

import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.StationConnector;

import java.util.List;

public interface StationConnectorService {
    List<StationConnector> getStationsConnectors();

    StationConnector getStationConnector(Long id);

    void createStationConnector(StationConnector stationConnector) throws ModelAttributeException;

    void deleteStationConnector(Long id) throws ModelNotFoundException;

    void updateStationConnector(Long id, StationConnector stationConnector) throws ModelAttributeException, ModelNotFoundException;
}
