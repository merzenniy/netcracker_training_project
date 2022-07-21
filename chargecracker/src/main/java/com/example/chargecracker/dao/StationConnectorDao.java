package com.example.chargecracker.dao;

import com.example.chargecracker.model.StationConnector;

import java.util.List;

public interface StationConnectorDao {
    List<StationConnector> getAll();

    StationConnector getById(Long id);

    void create(StationConnector stationConnector);

    void delete(Long id);

    void update(Long id, StationConnector stationConnector);
}
