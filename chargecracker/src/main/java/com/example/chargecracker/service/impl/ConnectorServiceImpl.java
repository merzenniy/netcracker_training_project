package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.ConnectorDAO;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.Connector;
import com.example.chargecracker.service.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectorServiceImpl implements ConnectorService {
    @Autowired
    private ConnectorDAO connectorDAO;

    @Override
    public List<Connector> getConnectors() {
        return connectorDAO.getAll();
    }

    @Override
    public Connector getConnector(Long id) {
        return connectorDAO.getById(id);
    }

    @Override
    public List<String> getConnectorTypeByStationId(Long id) {
        return connectorDAO.getTypeByStationId(id);
    }

    @Override
    public List<Connector> getConnectorsByStationId(Long id) {
        return connectorDAO.getAllByStationId(id);
    }

    @Override
    public void createConnector(Connector connector) throws ModelAttributeException, ModelAlreadyExistsException {
        validationConnector(connector);
        if (connectorDAO.getByType(connector.getType()) != null) {
            throw new ModelAlreadyExistsException("Connector type with name " + connector.getType() + " already exists");
        }
        connectorDAO.create(connector);
    }

    @Override
    public void deleteConnector(Long id) throws ModelNotFoundException {
        if (connectorDAO.getById(id) == null) {
            throw new ModelNotFoundException("Connector with id " + id + " not found");
        }
        connectorDAO.delete(id);
    }

    @Override
    public void updateConnector(Long id, Connector connector) throws ModelNotFoundException, ModelAttributeException {
        if (connectorDAO.getById(id) == null) {
            throw new ModelNotFoundException("Connector with id " + id + " not found");
        }
        validationConnector(connector);
        connectorDAO.update(id, connector);
    }

    private void validationConnector(Connector connector) throws ModelAttributeException {
        if (connector.getAmperage() <= 0 || connector.getPower() <= 0 || connector.getVoltage() <= 0
                || connector.getType() == null || connector.getType().isEmpty()) {
            throw new ModelAttributeException("Не правильно введенные данные");
        }
    }
}
