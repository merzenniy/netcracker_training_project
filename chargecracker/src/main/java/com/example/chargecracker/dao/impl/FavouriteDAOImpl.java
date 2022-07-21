package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.FavouriteDAO;

import com.example.chargecracker.model.Favourite;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class FavouriteDAOImpl implements FavouriteDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<Favourite> getAll() {
        List<Favourite> favourites = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM FAVOURITES");

            while (resultSet.next()) {
                favourites.add(fillFavourite(resultSet));
            }

            return favourites;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public Favourite getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM FAVOURITES WHERE FAVOURITE_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillFavourite(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Favourite getByUserIdAndStationId(Long userId, Long stationId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM FAVOURITES WHERE USER_ID = ? AND STATION_ID = ?");
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, stationId);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillFavourite(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private Favourite fillFavourite(ResultSet resultSet) throws SQLException {
        Favourite favourite = new Favourite();

        favourite.setId(resultSet.getLong("FAVOURITE_ID"));
        favourite.setStationId(resultSet.getLong("STATION_ID"));
        favourite.setUserId(resultSet.getLong("USER_ID"));

        return favourite;
    }

    @Override
    public void create(Favourite favourite) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO FAVOURITES (STATION_ID, USER_ID) VALUES(?, ?)");
            preparedStatement.setLong(1, favourite.getStationId());
            preparedStatement.setLong(2, favourite.getUserId());
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
            preparedStatement = connection.prepareStatement("DELETE FROM FAVOURITES WHERE FAVOURITE_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void delete(Long userId, Long stationId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM FAVOURITES WHERE USER_ID = ? AND STATION_ID = ?");
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, stationId);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, Favourite favourite) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE FAVOURITES SET " +
                    "USER_ID = ?, " +
                    "STATION_ID = ? " +
                    "WHERE FAVOURITE_ID = ?");
            preparedStatement.setLong(1, favourite.getUserId());
            preparedStatement.setLong(2, favourite.getStationId());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
