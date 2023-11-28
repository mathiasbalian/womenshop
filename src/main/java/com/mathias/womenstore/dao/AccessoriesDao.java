package com.mathias.womenstore.dao;

import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Accessory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccessoriesDao {

    public List<Accessory> getAccessories() {
        List<Accessory> accessories = new ArrayList<>();
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Accessory NATURAL JOIN Product;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Accessory accessory = new Accessory(resultSet.getInt("productId"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("nbItems"));
                accessories.add(accessory);
            }

            DbManager.close(connection, statement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accessories;
    }

}
