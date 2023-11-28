package com.mathias.womenstore.dao;

import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
