package com.mathias.womenstore.dao;

import com.mathias.womenstore.ProductCardController;
import com.mathias.womenstore.dbmanager.DbManager;
import com.mathias.womenstore.model.Accessory;
import com.mathias.womenstore.model.Clothes;
import com.mathias.womenstore.model.Product;
import com.mathias.womenstore.model.Shoe;
import javafx.scene.image.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public static void addProduct(Product product) {
        Connection connection = DbManager.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO Product VALUES (?, ?, ?, ?, 1);");
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getNbItems());
            statement.execute();
            statement.clearParameters();

            if (product instanceof Shoe) {
                statement = connection.prepareStatement("INSERT INTO Shoe VALUES (?, ?);");
                statement.setInt(1, product.getId());
                statement.setInt(2, ((Shoe) product).getShoeSize());
            } else if (product instanceof Clothes) {
                statement = connection.prepareStatement("INSERT INTO Clothes VALUES (?, ?);");
                statement.setInt(1, product.getId());
                statement.setInt(2, ((Clothes) product).getSize());
            } else {
                System.out.println("Accessory");
                statement = connection.prepareStatement("INSERT INTO Accessory VALUES (?);");
                statement.setInt(1, product.getId());
            }

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, null);
        }
    }

    public static Product assignProductClass(ResultSet resultSet) throws SQLException {
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

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Connection connection = DbManager.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
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
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                products.add(assignProductClass(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, resultSet);
        }

        return products;
    }

    public static void editProduct(Product product) {
        Connection connection = DbManager.getConnection();
        Statement statement = null;

        System.out.println("EDIT A PRODUCT");
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getId());
        try {
            statement = connection.createStatement();
            String query = "UPDATE Product SET name = '" + product.getName() +
                    "', price = " + product.getPrice() +
                    " WHERE productId = " + product.getId();
            statement.executeUpdate(query);

            if (product instanceof Shoe) {
                query = "UPDATE Shoe SET shoeSize = '" + ((Shoe) product).getShoeSize() +
                        "' WHERE productId = " + product.getId();
                statement.executeUpdate(query);

            } else if (product instanceof Clothes) {
                query = "UPDATE Clothes SET size = '" + ((Clothes) product).getSize() +
                        "' WHERE productId = " + product.getId();
                statement.executeUpdate(query);
            }

            // Print the contents of the Product table
            System.out.println("Contents of the Product table after update:");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Product");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("productId") +
                        ", Name: " + resultSet.getString("name") +
                        ", Price: " + resultSet.getDouble("price"));
            }

        } catch (SQLException e) {
            System.out.println("fail");
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, null);
        }
    }

    public static void updateProductStock(Product product, boolean increase) {
        Connection connection = DbManager.getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            String query = increase ? "UPDATE Product SET nbItems = nbItems + 1 WHERE productId = " + product.getId() :
                    "UPDATE Product SET nbItems = nbItems - 1 WHERE productId = " + product.getId();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, null);
        }
    }

    public static int getNextId() {
        Connection connection = DbManager.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            String query = "SELECT MAX(productId) + 1 FROM Product;";
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int id = resultSet.getInt(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(connection, statement, resultSet);
        }

        return 1;
    }
}
