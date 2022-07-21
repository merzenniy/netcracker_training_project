package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.StationConnectorDao;
import com.example.chargecracker.model.Favourite;
import com.example.chargecracker.model.StationConnector;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class StationConnectorDAOImpl implements StationConnectorDao {
    private DBManager dbManager = new DBManager();

    @Override
    public List<StationConnector> getAll() {
        List<StationConnector> stationConnectors = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM STATIONS_CONNECTORS");

            while (resultSet.next()) {
                stationConnectors.add(fillStationConnector(resultSet));
            }

            return stationConnectors;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public StationConnector getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM STATIONS_CONNECTORS WHERE INNER_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillStationConnector(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private StationConnector fillStationConnector(ResultSet resultSet) throws SQLException {
        StationConnector stationConnector = new StationConnector();

        stationConnector.setId(resultSet.getLong("INNER_ID"));
        stationConnector.setStationId(resultSet.getLong("STATION_ID"));
        stationConnector.setConnectorId(resultSet.getLong("CONNECTOR_ID"));

        return stationConnector;
    }

    @Override
    public void create(StationConnector stationConnector) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO STATIONS_CONNECTORS (STATION_ID, CONNECTOR_ID) VALUES(?, ?)");
            preparedStatement.setLong(1, stationConnector.getStationId());
            preparedStatement.setLong(2, stationConnector.getConnectorId());
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM STATIONS_CONNECTORS WHERE INNER_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, StationConnector stationConnector) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE STATIONS_CONNECTORS SET " +
                    "CONNECTOR_ID = ?, " +
                    "STATION_ID = ? " +
                    "WHERE INNER_ID = ?");
            preparedStatement.setLong(1, stationConnector.getConnectorId());
            preparedStatement.setLong(2, stationConnector.getStationId());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
