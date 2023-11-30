package com.mathias.womenstore.dao;

import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Shoe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShoesDao {

    public static List<Shoe> getShoes() {
        List<Shoe> shoes = new ArrayList<>();
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Shoe NATURAL JOIN Product;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Shoe shoe = new Shoe(resultSet.getInt("productId"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("nbItems"),
                        resultSet.getInt("shoeSize"));
                shoes.add(shoe);
            }

            DbManager.close(connection, statement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoes;
    }
}
