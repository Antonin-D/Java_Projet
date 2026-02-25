package com.mycompany.app.db.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceFactory {

    private DataSourceFactory() {
        throw new IllegalStateException("This is a static class that should not be instantiated");
    }

    /** URL de connexion JDBC à la base de données SQLite. */
    private static final String URL = "jdbc:sqlite:sqlite.db";
    
    public static Connection getConnection() {
        try { 
            Connection connection = DriverManager.getConnection(URL);
            
            // On s'assure que la table se crée automatiquement au démarrage !
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS person (" +
                        "idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "lastname VARCHAR(45) NOT NULL, " +
                        "firstname VARCHAR(45) NOT NULL, " +
                        "nickname VARCHAR(45) NOT NULL, " +
                        "phone_number VARCHAR(15) NULL, " +
                        "address VARCHAR(200) NULL, " +
                        "email_address VARCHAR(150) NULL, " +
                        "birth_date DATE NULL)");
            }
            
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de connexion à la base de données", e);
        }
    }
}