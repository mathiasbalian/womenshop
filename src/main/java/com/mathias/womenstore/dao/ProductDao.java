package com.mathias.womenstore.dao;

import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Accessory;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    public static Product createProduct(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("productId");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");
        int nbItems = resultSet.getInt("nbItems");
        Integer size = resultSet.getObject("size") != null ? resultSet.getInt("size") : null;
        Integer shoeSize = resultSet.getObject("shoeSize") != null ? resultSet.getInt("shoeSize") : null;

        if (size == null && shoeSize == null) {
            return new Accessory(productId, name, price, nbItems);
        } else if (shoeSize == null) {
            return new Clothes(productId, name, price, nbItems, size);
        } else {
            return new Shoe(productId, name, price, nbItems, shoeSize);
        }
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT \n" +
                    "    P.productId, \n" +
                    "    P.name, \n" +
                    "    P.price, \n" +
                    "    P.nbItems, \n" +
                    "    P.shopId, \n" +
                    "    C.size AS size,\n" +
                    "    NULL AS shoeSize\n" +
                    "FROM Product P\n" +
                    "NATURAL JOIN Clothes C\n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "SELECT \n" +
                    "    P.productId, \n" +
                    "    P.name, \n" +
                    "    P.price, \n" +
                    "    P.nbItems, \n" +
                    "    P.shopId, \n" +
                    "    NULL AS size,\n" +
                    "    S.shoeSize AS shoeSize\n" +
                    "FROM Product P\n" +
                    "NATURAL JOIN Shoe S\n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "SELECT \n" +
                    "    P.productId, \n" +
                    "    P.name, \n" +
                    "    P.price, \n" +
                    "    P.nbItems, \n" +
                    "    P.shopId, \n" +
                    "    NULL AS size,\n" +
                    "    NULL AS shoeSize\n" +
                    "FROM Product P\n" +
                    "NATURAL JOIN Accessory A";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                products.add(createProduct(resultSet));
            }

            DbManager.close(connection, statement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProductStock(Product product, boolean increase) {
        Connection connection = DbManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = increase ? "UPDATE Product SET nbItems = nbItems + 1 WHERE productId = " + product.getId() :
                    "UPDATE Product SET nbItems = nbItems - 1 WHERE productId = " + product.getId();
            statement.executeUpdate(query);
            DbManager.close(connection, statement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
