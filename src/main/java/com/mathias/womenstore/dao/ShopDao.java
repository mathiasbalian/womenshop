package com.mathias.womenstore.dao;

import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShopDao {

    public static Shop getShop() {
        Shop shop = new Shop();
        Connection connection = DbManager.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM Shop;";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                shop = new Shop(
                        resultSet.getDouble("income"),
                        resultSet.getDouble("cost"),
                        resultSet.getDouble("capital")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, resultSet);
        }

        return shop;
    }

    public static void setCapital(double newCapital) {
        Connection connection = DbManager.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String query = "UPDATE Shop SET capital = " + newCapital;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, null);
        }
    }

    public static void setIncome(double newIncome) {
        Connection connection = DbManager.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String query = "UPDATE Shop SET income = " + newIncome;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, null);
        }
    }

    public static void setCost(double newCost) {
        Connection connection = DbManager.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String query = "UPDATE Shop SET cost = " + newCost;
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, null);
        }
    }

}
