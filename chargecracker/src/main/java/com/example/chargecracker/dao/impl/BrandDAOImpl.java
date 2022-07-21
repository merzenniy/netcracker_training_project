package com.example.chargecracker.dao.impl;

import com.example.chargecracker.dao.BrandDAO;
import com.example.chargecracker.dao.DBManager;
import com.example.chargecracker.model.Brand;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandDAOImpl implements BrandDAO {
    private DBManager dbManager;

    public BrandDAOImpl() {
        this.dbManager = new DBManager();
    }

    @Override
    public List<Brand> getAll() {
        List<Brand> brands = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM BRANDS");

            while (resultSet.next()) {
                Brand brand = new Brand();
                brand.setId(resultSet.getLong("BRAND_ID"));
                brand.setName(resultSet.getString("NAME"));

                brands.add(brand);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, statement, resultSet);
        }

        return brands;
    }

    @Override
    public Brand getById(Long id) {
        Brand brand = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement("SELECT * FROM BRANDS WHERE BRAND_ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            brand = new Brand();
            brand.setId(resultSet.getLong("BRAND_ID"));
            brand.setName(resultSet.getString("NAME"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return brand;
    }

    @Override
    public Brand getByName(String name) {
        Brand brand = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement("SELECT * FROM BRANDS WHERE NAME = ?");
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            brand = new Brand();
            brand.setId(resultSet.getLong("BRAND_ID"));
            brand.setName(resultSet.getString("NAME"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement, resultSet);
        }

        return brand;
    }

    @Override
    public void create(Brand brand) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO BRANDS (NAME) VALUES (?)");
            preparedStatement.setString(1, brand.getName());
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
                            "DELETE BRANDS WHERE BRAND_ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }

    @Override
    public void update(Long id, Brand brand) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dbManager.getConnection();
            preparedStatement =
                    connection.prepareStatement(
                            "UPDATE BRANDS SET NAME = ? WHERE BRAND_ID = ?");
            preparedStatement.setString(1, brand.getName());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            dbManager.closeResource(connection, preparedStatement);
        }
    }
}
