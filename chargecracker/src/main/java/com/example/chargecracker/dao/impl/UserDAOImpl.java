package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.UserDAO;
import com.example.chargecracker.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private DBManager dbManager;

    public UserDAOImpl() {
        this.dbManager = new DBManager();
    }

    private void userResultSet(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("USER_ID"));
        user.setLastName(resultSet.getString("LASTNAME"));
        user.setFirstName(resultSet.getString("FIRSTNAME"));
        user.setMidName(resultSet.getString("MIDNAME"));
        user.setMail(resultSet.getString("MAIL"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setAutoId(resultSet.getLong("AUTO_ID"));
        user.setRoleId(resultSet.getLong("ROLE_ID"));
    }

    private void userPreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getLastName());
        preparedStatement.setString(2, user.getFirstName());
        preparedStatement.setString(3, user.getMidName());
        preparedStatement.setString(4, user.getMail());
        preparedStatement.setString(5, user.getPassword());
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM USERS");

            while (resultSet.next()) {
                User user = new User();
                userResultSet(user, resultSet);

                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return users;
    }

    @Override
    public User getById(Long id) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement("SELECT * FROM USERS WHERE USER_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            user = new User();
            userResultSet(user, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return user;
    }

    @Override
    public User getByMail(String mail) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement("SELECT * FROM USERS WHERE MAIL = ?");
            preparedStatement.setString(1, mail);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            user = new User();
            userResultSet(user, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return user;
    }


    @Override
    public void create(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO USERS(LASTNAME, FIRSTNAME, MIDNAME, MAIL, PASSWORD, ROLE_ID) " +
                                    "VALUES(?, ?, ?, ?, ?, ?)");
            userPreparedStatement(user, preparedStatement);
            preparedStatement.setLong(6, user.getRoleId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void createFullUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO USERS(LASTNAME, FIRSTNAME, MIDNAME, MAIL, PASSWORD, ROLE_ID, AUTO_ID) " +
                                    "VALUES(?, ?, ?, ?, ?, ?, ?)");
            userPreparedStatement(user, preparedStatement);
            preparedStatement.setLong(6, user.getRoleId());
            if (user.getAutoId() == null || user.getAutoId() == 0) {
                preparedStatement.setString(7, null);
            } else {
                preparedStatement.setLong(7, user.getAutoId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            preparedStatement =
                    connection.prepareStatement(
                            "DELETE USERS WHERE USER_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "UPDATE USERS SET " +
                                    "LASTNAME = ?, FIRSTNAME = ?, " +
                                    "MIDNAME = ?, MAIL =? , PASSWORD = ?" +
                                    "WHERE USER_ID = ?");
            userPreparedStatement(user, preparedStatement);
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void updateFullUser(Long id, User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "UPDATE USERS SET " +
                                    "LASTNAME = ?, FIRSTNAME = ?, " +
                                    "MIDNAME = ?, MAIL =? , PASSWORD = ?," +
                                    "ROLE_ID = ?, AUTO_ID = ?" +
                                    "WHERE USER_ID = ?");
            preparedStatement.setString(1, user.getLastName());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getMidName());
            preparedStatement.setString(4, user.getMail());
            if (user.getPassword() != null) {
                preparedStatement.setString(5, user.getPassword());
            }
            preparedStatement.setLong(6, user.getRoleId());
            if (user.getAutoId() == null || user.getAutoId() == 0) {
                preparedStatement.setString(7, null);
            } else {
                preparedStatement.setLong(7, user.getAutoId());
            }
            preparedStatement.setLong(8, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void updateAuto(Long userId, Long autoId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "UPDATE USERS SET AUTO_ID = ? WHERE USER_ID = ?");
            preparedStatement.setLong(1, autoId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void updatePassword(Long id, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "UPDATE USERS SET PASSWORD = ? WHERE USER_ID = ?");
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
