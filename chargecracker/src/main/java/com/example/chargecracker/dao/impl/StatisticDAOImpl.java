package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.StatisticDAO;
import com.example.chargecracker.dto.NamedStatisticDto;
import com.example.chargecracker.dto.StatisticDto;
import com.example.chargecracker.model.Statistic;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Repository
public class StatisticDAOImpl implements StatisticDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<StatisticDto> getAll() {
        List<StatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT TO_CHAR(CREATED_AT, 'yyyy') AS CREATED_AT, COUNT(CREATED_AT) AS COUNT " +
                    "FROM STATISTICS " +
                    "WHERE OPERATION_TYPE = 'GET' " +
                    "GROUP BY TO_CHAR(CREATED_AT, 'yyyy') " +
                    "ORDER BY CREATED_AT");

            while (resultSet.next()) {
                StatisticDto statisticDto = new StatisticDto();
                statisticDto.setCount(resultSet.getLong("COUNT"));
                statisticDto.setCreatedAt(resultSet.getString("CREATED_AT"));
                result.add(statisticDto);
            }

            return result;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<StatisticDto> getAllByCurrentYear() {
        List<StatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT TO_CHAR(CREATED_AT, 'MM') AS CREATED_AT, COUNT(CREATED_AT) AS COUNT " +
                    "FROM STATISTICS " +
                    "WHERE OPERATION_TYPE = 'GET' AND TO_CHAR(CREATED_AT, 'YYYY') = to_char(sysdate, 'YYYY') " +
                    "GROUP BY TO_CHAR(CREATED_AT, 'MM') " +
                    "ORDER BY CREATED_AT");

            while (resultSet.next()) {
                StatisticDto statisticDto = new StatisticDto();
                statisticDto.setCount(resultSet.getLong("COUNT"));
                statisticDto.setCreatedAt(resultSet.getString("CREATED_AT"));
                result.add(statisticDto);
            }

            return result;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<StatisticDto> getAllByCurrentMonth() {
        List<StatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT TO_CHAR(CREATED_AT, 'DD') AS CREATED_AT, COUNT(CREATED_AT) AS COUNT " +
                    "FROM STATISTICS " +
                    "WHERE OPERATION_TYPE = 'GET' AND TO_CHAR(CREATED_AT, 'MM-YYYY') = TO_CHAR(SYSDATE, 'MM-YYYY') " +
                    "GROUP BY TO_CHAR(CREATED_AT, 'DD') " +
                    "ORDER BY CREATED_AT");

            while (resultSet.next()) {
                StatisticDto statisticDto = new StatisticDto();
                statisticDto.setCount(resultSet.getLong("COUNT"));
                statisticDto.setCreatedAt(resultSet.getString("CREATED_AT"));
                result.add(statisticDto);
            }

            return result;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<NamedStatisticDto> getAllPage() {
        List<NamedStatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT BUSINESS_OPERATION_NAME LABEL, TO_CHAR(CREATED_AT, 'yyyy') AS CREATED_AT, " +
                            "COUNT(BUSINESS_OPERATION_NAME) AS COUNT " +
                            "FROM STATISTICS " +
                            "WHERE BUSINESS_OPERATION_NAME IS NOT NULL AND OPERATION_TYPE = 'GET'" +
                            "GROUP BY BUSINESS_OPERATION_NAME, TO_CHAR(CREATED_AT, 'yyyy')" +
                            "ORDER BY CREATED_AT");

            return getPageStatisticDtos(result, resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    private List<NamedStatisticDto> getPageStatisticDtos(List<NamedStatisticDto> result, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            NamedStatisticDto namedStatisticDto = new NamedStatisticDto();
            namedStatisticDto.setCount(resultSet.getLong("COUNT"));
            namedStatisticDto.setCreatedAt(resultSet.getString("CREATED_AT"));
            namedStatisticDto.setLabel(resultSet.getString("LABEL"));
            result.add(namedStatisticDto);
        }

        return result;
    }

    @Override
    public List<NamedStatisticDto> getAllPageByCurrentYear() {
        List<NamedStatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT BUSINESS_OPERATION_NAME LABEL, TO_CHAR(CREATED_AT, 'MM') AS CREATED_AT, " +
                    "COUNT(BUSINESS_OPERATION_NAME) AS COUNT " +
                    "FROM STATISTICS " +
                    "WHERE BUSINESS_OPERATION_NAME IS NOT NULL " +
                    "AND OPERATION_TYPE = 'GET' " +
                    "AND TO_CHAR(CREATED_AT, 'YYYY') = to_char(sysdate, 'YYYY') " +
                    "GROUP BY BUSINESS_OPERATION_NAME, TO_CHAR(CREATED_AT, 'MM') " +
                    "ORDER BY CREATED_AT");

            return getPageStatisticDtos(result, resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<NamedStatisticDto> getAllPageByCurrentMonth() {
        List<NamedStatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT BUSINESS_OPERATION_NAME LABEL, TO_CHAR(CREATED_AT, 'DD') AS CREATED_AT, " +
                    "COUNT(BUSINESS_OPERATION_NAME) AS COUNT " +
                    "FROM STATISTICS " +
                    "WHERE BUSINESS_OPERATION_NAME IS NOT NULL " +
                    "AND OPERATION_TYPE = 'GET' " +
                    "AND TO_CHAR(CREATED_AT, 'MM-YYYY') = TO_CHAR(SYSDATE, 'MM-YYYY') " +
                    "GROUP BY BUSINESS_OPERATION_NAME, TO_CHAR(CREATED_AT, 'DD') " +
                    "ORDER BY CREATED_AT");

            return getPageStatisticDtos(result, resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<NamedStatisticDto> getAllStationsHours() {
        List<NamedStatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT 'STATION_ID ' || STATION_ID LABEL, SUM(ABS(EXTRACT(DAY FROM DIFF))) COUNT, CREATED_AT " +
                    "FROM ( " +
                    "SELECT S.STATION_ID, TO_CHAR(R.CREATED_AT, 'YYYY') CREATED_AT, ((R.TIME_END - R.TIME) * 24) DIFF " +
                    "FROM RESERVATIONS R, STATIONS S " +
                    "WHERE R.STATION_ID = S.STATION_ID)" +
                    "GROUP BY STATION_ID, CREATED_AT " +
                    "ORDER BY CREATED_AT, STATION_ID");

            return getPageStatisticDtos(result, resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<NamedStatisticDto> getAllStationHoursYear() {
        List<NamedStatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT 'STATION_ID ' || STATION_ID LABEL, SUM(ABS(EXTRACT(DAY FROM DIFF))) COUNT, CREATED_AT " +
                    "FROM ( " +
                    "SELECT S.STATION_ID, TO_CHAR(CREATED_AT, 'MM') CREATED_AT, ((R.TIME_END - R.TIME) * 24) DIFF " +
                    "FROM RESERVATIONS R, STATIONS S " +
                    "WHERE R.STATION_ID = S.STATION_ID AND TO_CHAR(CREATED_AT, 'YYYY') = TO_CHAR(SYSDATE, 'YYYY')) " +
                    "GROUP BY STATION_ID, CREATED_AT " +
                    "ORDER BY CREATED_AT, STATION_ID");

            return getPageStatisticDtos(result, resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public List<NamedStatisticDto> getAllStationHoursMonth() {
        List<NamedStatisticDto> result = new LinkedList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("" +
                    "SELECT 'STATION_ID ' || STATION_ID LABEL, SUM(ABS(EXTRACT(DAY FROM DIFF))) COUNT, CREATED_AT " +
                    "FROM ( " +
                    "SELECT S.STATION_ID, TO_CHAR(CREATED_AT, 'DD') CREATED_AT, ((R.TIME_END - R.TIME) * 24) DIFF " +
                    "FROM RESERVATIONS R, STATIONS S " +
                    "WHERE R.STATION_ID = S.STATION_ID AND TO_CHAR(CREATED_AT, 'MM-YYYY') = TO_CHAR(SYSDATE, 'MM-YYYY')) " +
                    "GROUP BY STATION_ID, CREATED_AT " +
                    "ORDER BY CREATED_AT, STATION_ID");

            return getPageStatisticDtos(result, resultSet);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return null;
    }

    @Override
    public void create(Statistic statistic) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO STATISTICS (URI, OPERATION_TYPE, CREATED_AT, BUSINESS_OPERATION_NAME) " +
                    "VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, statistic.getUri());
            preparedStatement.setString(2, statistic.getOperationType());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            preparedStatement.setString(3, formatter.format(statistic.getCreatedAt()));
            if (statistic.getBusinessOperationName() == null) {
                preparedStatement.setString(4, null);
            } else {
                preparedStatement.setString(4, statistic.getBusinessOperationName());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
