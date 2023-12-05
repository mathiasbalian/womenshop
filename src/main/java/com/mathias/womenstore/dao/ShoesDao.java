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
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM Shoe NATURAL JOIN Product;";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Shoe shoe = new Shoe(
                        resultSet.getInt("productId"),
                        resultSet.getString("name"),
                        resultSet.getDouble("realPrice"),
                        resultSet.getDouble("currentPrice"),
                        resultSet.getInt("nbItems"),
                        resultSet.getInt("shoeSize")
                );
                shoes.add(shoe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, resultSet);
        }

        return shoes;
    }
}
