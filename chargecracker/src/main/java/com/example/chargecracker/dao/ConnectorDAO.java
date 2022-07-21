package com.example.chargecracker.dao;

import com.example.chargecracker.model.Connector;

import java.util.List;

public interface ConnectorDAO {
    List<Connector> getAll();

    Connector getById(Long id);

    Connector getByType(String type);

    List<String> getTypeByStationId(Long id);

    List<Connector> getAllByStationId(Long id);

    void create(Connector connector);

    void delete(Long id);

    void update(Long id, Connector connector);
}
