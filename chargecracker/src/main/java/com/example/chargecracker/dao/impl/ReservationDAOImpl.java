package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.ReservationDAO;
import com.example.chargecracker.dto.ReservationDto;
import com.example.chargecracker.model.Reservation;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ReservationDAOImpl implements ReservationDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM RESERVATIONS");

            while (resultSet.next()) {
                reservations.add(fillReservation(resultSet));
            }
            return reservations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<ReservationDto> getAllUser(Long id) {
        List<ReservationDto> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT R.RESERVATION_ID, R.CODE, " +
                    "TO_CHAR(R.TIME, 'dd.mm.yyyy hh:mm') START_TIME, " +
                    "TO_CHAR(R.TIME_END, 'dd.mm.yyyy hh:mm') END_TIME, S.CORDINATES " +
                    "FROM RESERVATIONS R, STATIONS S " +
                    "WHERE R.USER_ID = ? AND R.STATION_ID = S.STATION_ID AND R.TIME_END > SYSTIMESTAMP " +
                    "ORDER BY START_TIME, END_TIME");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReservationDto reservationDto = new ReservationDto();
                reservationDto.setCode(resultSet.getString("CODE"));
                reservationDto.setCordinates(resultSet.getString("CORDINATES"));
                reservationDto.setReservationId(resultSet.getLong("RESERVATION_ID"));
                reservationDto.setStartTime(resultSet.getString("START_TIME"));
                reservationDto.setEndTime(resultSet.getString("END_TIME"));
                result.add(reservationDto);
            }

            if (result.size() == 0) {
                return null;
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
    public Reservation getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM RESERVATIONS WHERE RESERVATION_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillReservation(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Boolean isReservationExist(Long id, String startTimestamp, String endTimestamp) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * " +
                    "FROM RESERVATIONS " +
                    "WHERE STATION_ID = ? AND (TO_DATE(?, 'dd.mm.yyyy HH24:MI:SS') BETWEEN TIME AND TIME_END " +
                    "OR TO_DATE(?, 'dd.mm.yyyy HH24:MI:SS') BETWEEN TIME AND TIME_END)");
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, startTimestamp);
            preparedStatement.setString(3, endTimestamp);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private Reservation fillReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();

        reservation.setId(resultSet.getLong("RESERVATION_ID"));
        reservation.setTime(resultSet.getTimestamp("TIME"));
        reservation.setEndTime(resultSet.getTimestamp("TIME_END"));
        reservation.setCode(resultSet.getString("CODE"));
        reservation.setStationId(resultSet.getLong("STATION_ID"));
        reservation.setUserId(resultSet.getLong("USER_ID"));

        return reservation;
    }

    @Override
    public void create(Reservation reservation) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO RESERVATIONS (TIME, TIME_END, CODE ,STATION_ID, USER_ID, CREATED_AT) VALUES(?, ?, ?, ?, ?, ?)");
            preparedStatement.setTimestamp(1, reservation.getTime());
            preparedStatement.setTimestamp(2, reservation.getEndTime());
            preparedStatement.setString(3, reservation.getCode());
            preparedStatement.setLong(4, reservation.getStationId());
            preparedStatement.setLong(5, reservation.getUserId());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            preparedStatement.setString(6, formatter.format(new Date()));
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
            preparedStatement = connection.prepareStatement("DELETE FROM RESERVATIONS WHERE RESERVATION_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, Reservation reservation) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE RESERVATIONS SET " +
                    "TIME = ?," +
                    "TIME_END = ?," +
                    "CODE = ?, " +
                    "USER_ID = ?, " +
                    "STATION_ID = ? " +
                    "WHERE RESERVATION_ID = ?");
            preparedStatement.setTimestamp(1, reservation.getTime());
            preparedStatement.setTimestamp(2, reservation.getEndTime());
            preparedStatement.setString(3, reservation.getCode());
            preparedStatement.setLong(4, reservation.getUserId());
            preparedStatement.setLong(5, reservation.getStationId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
