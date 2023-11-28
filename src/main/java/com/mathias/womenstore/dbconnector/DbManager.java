package com.mathias.womenstore.dbconnector;

import java.sql.*;

public abstract class DbManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/store?serverTimezone=Europe%2FParis";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/store?serverTimezone=Europe%2FParis",
                    "root",
                    "root");
            return connection;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null) connection.close();
            if (statement != null) statement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
