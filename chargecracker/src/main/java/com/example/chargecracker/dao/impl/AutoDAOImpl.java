package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.AutoDAO;
import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.model.Auto;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class AutoDAOImpl implements AutoDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<Auto> getAll() {
        List<Auto> autos = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM AUTOS");

            while (resultSet.next()) {
                autos.add(fillAuto(resultSet));
            }

            return autos;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public Auto getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM AUTOS WHERE AUTO_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillAuto(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private Auto fillAuto(ResultSet resultSet) throws SQLException {
        Auto auto = new Auto();

        auto.setId(resultSet.getLong("AUTO_ID"));
        auto.setBrandId(resultSet.getLong("BRAND_ID"));
        auto.setModel(resultSet.getString("MODEL"));
        auto.setMaxCharge(resultSet.getInt("MAX_CHARGE"));
        auto.setConnectorId(resultSet.getLong("CONNECTOR_ID"));

        return auto;
    }

    @Override
    public Long create(Auto auto) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String[] returnId = { "AUTO_ID" };
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO AUTOS (BRAND_ID, MODEL, MAX_CHARGE, CONNECTOR_ID) VALUES(?, ?, ?, ?)", returnId);
            preparedStatement.setLong(1, auto.getBrandId());
            preparedStatement.setString(2, auto.getModel());
            preparedStatement.setInt(3, auto.getMaxCharge());
            preparedStatement.setLong(4, auto.getConnectorId());
            preparedStatement.executeUpdate();


            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM AUTOS WHERE AUTO_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, Auto auto) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE AUTOS SET " +
                    "BRAND_ID = ?, " +
                    "MODEL = ?," +
                    "MAX_CHARGE = ?," +
                    "CONNECTOR_ID = ? " +
                    "WHERE AUTO_ID = ?");
            preparedStatement.setLong(1, auto.getBrandId());
            preparedStatement.setString(2, auto.getModel());
            preparedStatement.setInt(3, auto.getMaxCharge());
            preparedStatement.setLong(4, auto.getConnectorId());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
