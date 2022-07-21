package com.example.chargecracker.dao;

import java.sql.*;

public class DBManager {
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/XEPDB1";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public void closeResource(Connection connection, Statement statement, ResultSet resultSet, PreparedStatement preparedStatement) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
        closeResource(connection, statement, resultSet, null);
    }

    public void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        closeResource(connection, null, resultSet, preparedStatement);
    }

    public void closeResource(Connection connection, PreparedStatement preparedStatement) {
        closeResource(connection, null, null, preparedStatement);
    }
}
