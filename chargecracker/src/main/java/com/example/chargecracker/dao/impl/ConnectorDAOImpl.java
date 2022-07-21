package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.ConnectorDAO;
import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.model.Auto;
import com.example.chargecracker.model.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ConnectorDAOImpl implements ConnectorDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<Connector> getAll() {
        List<Connector> connectors = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM CONNECTORS");

            while (resultSet.next()) {
                connectors.add(fillConnector(resultSet));
            }

            return connectors;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public Connector getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM CONNECTORS WHERE CONNECTOR_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillConnector(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Connector getByType(String type) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM CONNECTORS WHERE TYPE = ?");
            preparedStatement.setString(1, type);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillConnector(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<String> getTypeByStationId(Long id) {
        List<String> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT TYPE " +
                    "FROM CONNECTORS C, STATIONS_CONNECTORS SC " +
                    "WHERE SC.STATION_ID = ? AND C.CONNECTOR_ID = SC.CONNECTOR_ID");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("TYPE"));
            }

            return result;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<Connector> getAllByStationId(Long id) {
        List<Connector> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT C.CONNECTOR_ID, C.TYPE, C.AMPERAGE, C.VOLTAGE, C.POWER " +
                    "FROM CONNECTORS C, STATIONS_CONNECTORS SC " +
                    "WHERE SC.STATION_ID = ? AND C.CONNECTOR_ID = SC.CONNECTOR_ID");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(fillConnector(resultSet));
            }

            return result;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private Connector fillConnector(ResultSet resultSet) throws SQLException {
        Connector connector = new Connector();

        connector.setId(resultSet.getLong("CONNECTOR_ID"));
        connector.setType(resultSet.getString("TYPE"));
        connector.setAmperage(resultSet.getInt("AMPERAGE"));
        connector.setVoltage(resultSet.getInt("VOLTAGE"));
        connector.setPower(resultSet.getFloat("POWER"));

        return connector;
    }

    @Override
    public void create(Connector connector) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO CONNECTORS (TYPE, AMPERAGE, VOLTAGE, POWER) VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, connector.getType());
            preparedStatement.setInt(2, connector.getAmperage());
            preparedStatement.setInt(3, connector.getVoltage());
            preparedStatement.setFloat(4, connector.getPower());
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
            preparedStatement = connection.prepareStatement("DELETE FROM CONNECTORS WHERE CONNECTOR_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, Connector connector) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE CONNECTORS SET " +
                    "TYPE = ?," +
                    "AMPERAGE = ?," +
                    "VOLTAGE = ?, " +
                    "POWER = ? " +
                    "WHERE CONNECTOR_ID = ?");
            preparedStatement.setString(1, connector.getType());
            preparedStatement.setInt(2, connector.getAmperage());
            preparedStatement.setInt(3, connector.getVoltage());
            preparedStatement.setFloat(4, connector.getPower());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
