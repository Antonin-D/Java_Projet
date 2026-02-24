package com.mycompany.app.db.daos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceFactory {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:sqlite.db");
    }
}