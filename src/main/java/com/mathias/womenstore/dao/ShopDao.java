package com.mathias.womenstore.dao;

import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Shop;

import java.sql.*;

public class ShopDao {
    
    public Shop getShop() {
        Shop shop = new Shop();
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Shop;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                shop = new Shop(resultSet.getDouble("income"),
                        resultSet.getDouble("cost"),
                        resultSet.getDouble("capital"));
            }

            DbManager.close(connection, statement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    public void setCapital(double newCapital) {
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Shop SET capital = " + newCapital;
            int rowsAffected = statement.executeUpdate(query);
            System.out.println(rowsAffected + " row(s) affected.");
            DbManager.close(connection, statement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setIncome(double newIncome) {
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Shop SET income = " + newIncome;
            int rowsAffected = statement.executeUpdate(query);
            System.out.println(rowsAffected + " row(s) affected.");
            DbManager.close(connection, statement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCost(double newCost) {
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE Shop SET cost = " + newCost;
            int rowsAffected = statement.executeUpdate(query);
            System.out.println(rowsAffected + " row(s) affected.");
            DbManager.close(connection, statement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
