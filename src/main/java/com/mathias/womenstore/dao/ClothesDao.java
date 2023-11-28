package com.mathias.womenstore.dao;

import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Clothes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClothesDao {

    public List<Clothes> getClothes() {
        List<Clothes> allClothes = new ArrayList<>();
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Clothes NATURAL JOIN Product;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Clothes clothes = new Clothes(resultSet.getInt("productId"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("nbItems"),
                        resultSet.getInt("size"));
                allClothes.add(clothes);
            }

            DbManager.close(connection, statement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allClothes;
    }
}
