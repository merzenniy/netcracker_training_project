package com.example.chargecracker.service;

import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Connector;

import java.util.List;

public interface ConnectorService {
    List<Connector> getConnectors();

    Connector getConnector(Long id);

    List<String> getConnectorTypeByStationId(Long id);

    List<Connector> getConnectorsByStationId(Long id);

    void createConnector(Connector connector) throws ModelAttributeException, ModelAlreadyExistsException;

    void deleteConnector(Long id) throws ModelNotFoundException;

    void updateConnector(Long id, Connector connector) throws ModelNotFoundException, ModelAttributeException, ModelAlreadyExistsException;
}
