package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.RateDAO;
import com.example.chargecracker.dto.RateDto;
import com.example.chargecracker.model.Rate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class RateDAOImpl implements RateDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<Rate> getAll() {
        List<Rate> rates = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM RATES");

            while (resultSet.next()) {
                rates.add(fillRate(resultSet));
            }

            return rates;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public Rate getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM RATES WHERE RATE_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillRate(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<RateDto> getByStationId(Long id) {
        List<RateDto> rateList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT U.USER_ID, R.RATE_ID, U.LASTNAME || ' ' || U.FIRSTNAME FULL_NAME, R.RATE, R.COMMENTARY " +
                    "FROM RATES R, USERS U " +
                    "WHERE STATION_ID = ? AND U.USER_ID = R.USER_ID");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                RateDto rateDto = new RateDto();
                rateDto.setRateId(resultSet.getLong("RATE_ID"));
                rateDto.setUserId(resultSet.getLong("USER_ID"));
                rateDto.setRate(resultSet.getFloat("RATE"));
                rateDto.setFullName(resultSet.getString("FULL_NAME"));
                rateDto.setCommentary(resultSet.getString("COMMENTARY"));
                rateList.add(rateDto);
            }

            return rateList;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Boolean isRateExist(Long userId, Long stationId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT CASE WHEN RATE_ID IS NOT NULL THEN 'TRUE' ELSE 'FALSE' END RATE_EXIST " +
                    "FROM RATES " +
                    "WHERE USER_ID = ? AND STATION_ID = ? ");
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, stationId);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private Rate fillRate(ResultSet resultSet) throws SQLException {
        Rate rate = new Rate();

        rate.setId(resultSet.getLong("RATE_ID"));
        rate.setStationId(resultSet.getLong("STATION_ID"));
        rate.setUserId(resultSet.getLong("USER_ID"));
        rate.setRate(resultSet.getFloat("RATE"));
        rate.setCommentary(resultSet.getString("COMMENTARY"));

        return rate;
    }

    @Override
    public void create(Rate rate) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO RATES (STATION_ID, USER_ID, RATE, COMMENTARY) VALUES(?, ?, ?, ?)");
            preparedStatement.setLong(1, rate.getStationId());
            preparedStatement.setLong(2, rate.getUserId());
            preparedStatement.setFloat(3, rate.getRate());
            preparedStatement.setString(4, rate.getCommentary());
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
            preparedStatement = connection.prepareStatement("DELETE FROM RATES WHERE RATE_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, Rate rate) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE RATES SET " +
                    "USER_ID = ?, " +
                    "STATION_ID = ?," +
                    "RATE = ?," +
                    "COMMENTARY = ? " +
                    "WHERE RATE_ID = ?");
            preparedStatement.setLong(1, rate.getUserId());
            preparedStatement.setLong(2, rate.getStationId());
            preparedStatement.setFloat(3, rate.getRate());
            preparedStatement.setString(4, rate.getCommentary());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
