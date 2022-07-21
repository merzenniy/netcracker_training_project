package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.StationDAO;
import com.example.chargecracker.model.Station;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class StationDAOImpl implements StationDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<Station> getAll() {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM STATIONS");

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllOrderByPriceAsc() {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM STATIONS ORDER BY PRICE");

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllOrderByPriceDesc() {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM STATIONS ORDER BY PRICE DESC");

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllOrderByRateAsc() {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM STATIONS ORDER BY RATE");

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllOrderByRateDesc() {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM STATIONS ORDER BY RATE DESC");

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllFavouriteOrderByPriceAsc(Long id) {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT S.STATION_ID, S.CORDINATES, S.RATE, S.PRICE, S.STATUS " +
                    "FROM FAVOURITES F, STATIONS S " +
                    "WHERE F.USER_ID = ? AND F.STATION_ID = S.STATION_ID ORDER BY PRICE");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllFavouriteOrderByPriceDesc(Long id) {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT S.STATION_ID, S.CORDINATES, S.RATE, S.PRICE, S.STATUS " +
                    "FROM FAVOURITES F, STATIONS S " +
                    "WHERE F.USER_ID = ? AND F.STATION_ID = S.STATION_ID ORDER BY PRICE DESC");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllFavouriteOrderByRateAsc(Long id) {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT S.STATION_ID, S.CORDINATES, S.RATE, S.PRICE, S.STATUS " +
                    "FROM FAVOURITES F, STATIONS S " +
                    "WHERE F.USER_ID = ? AND F.STATION_ID = S.STATION_ID ORDER BY RATE");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllFavouriteOrderByRateDesc(Long id) {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT S.STATION_ID, S.CORDINATES, S.RATE, S.PRICE, S.STATUS " +
                    "FROM FAVOURITES F, STATIONS S " +
                    "WHERE F.USER_ID = ? AND F.STATION_ID = S.STATION_ID ORDER BY RATE DESC ");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllUserFavourite(Long id) {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT S.STATION_ID, S.CORDINATES, S.RATE, S.PRICE, S.STATUS " +
                    "FROM FAVOURITES F, STATIONS S " +
                    "WHERE F.USER_ID = ? AND F.STATION_ID = S.STATION_ID");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public List<Station> getAllByUserAuto(Long id) {
        List<Station> stations = new LinkedList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT S.STATION_ID, S.CORDINATES, S.RATE, S.PRICE, S.STATUS " +
                    "FROM USERS U, AUTOS A, CONNECTORS C, STATIONS_CONNECTORS SC, STATIONS S " +
                    "WHERE U.USER_ID = ? " +
                    "AND U.AUTO_ID = A.AUTO_ID " +
                    "AND C.CONNECTOR_ID = A.CONNECTOR_ID " +
                    "AND SC.CONNECTOR_ID = C.CONNECTOR_ID " +
                    "AND S.STATION_ID = SC.STATION_ID");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stations.add(fillStation(resultSet));
            }

            return stations;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Station getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM STATIONS WHERE STATION_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            return fillStation(resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private Station fillStation(ResultSet resultSet) throws SQLException {
        Station station = new Station();
        station.setId(resultSet.getLong("STATION_ID"));
        station.setCordinates(resultSet.getString("CORDINATES"));
        station.setRate(resultSet.getFloat("RATE"));
        station.setPrice(resultSet.getFloat("PRICE"));
        station.setStatus(resultSet.getString("STATUS"));

        return station;
    }

    @Override
    public void create(Station station) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO STATIONS(CORDINATES, PRICE, STATUS) VALUES(?, ?, ?)");
            preparedStatement.setString(1, station.getCordinates());
            preparedStatement.setFloat(2, station.getPrice());
            preparedStatement.setString(3, station.getStatus());
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
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM STATIONS WHERE STATION_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, Station station) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE STATIONS SET " +
                            "CORDINATES = ?, " +
                            "PRICE = ?, " +
                            "STATUS = ? " +
                            "WHERE STATION_ID = ?");
            preparedStatement.setString(1, station.getCordinates());
            preparedStatement.setFloat(2, station.getPrice());
            preparedStatement.setString(3, station.getStatus());
            preparedStatement.setLong(4, station.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void updateRate(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement("" +
                            "UPDATE STATIONS SET RATE = " +
                            "(SELECT SUM(RATE) / COUNT(RATE_ID)" +
                            "FROM RATES  " +
                            "WHERE STATION_ID = ? " +
                            "GROUP BY STATION_ID) " +
                            "WHERE STATION_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
