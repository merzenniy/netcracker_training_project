package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.dao.RoleDAO;
import com.example.chargecracker.model.Role;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private DBManager dbManager = new DBManager();

    @Override
    public List<Role> getAll() {
        List<Role> result = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM ROLES");

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getLong("ROLE_ID"));
                role.setName(resultSet.getString("NAME"));
                result.add(role);
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
    public Role getById(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM ROLES WHERE ROLE_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Role role = new Role();
            role.setId(resultSet.getLong("ROLE_ID"));
            role.setName(resultSet.getString("NAME"));

            return role;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Role getByName(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM ROLES WHERE NAME = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Role role = new Role();
            role.setId(resultSet.getLong("ROLE_ID"));
            role.setName(resultSet.getString("NAME"));

            return role;
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return null;
    }
}
